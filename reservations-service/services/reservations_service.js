const organizationsService = require('./organizations_service');
const booksService = require('./books_service');
const usersService = require('./users_service');
const penaltiesService = require('./penalties_service');
const {ClientSideError} = require('./errors/client_side_error');
const {Sequelize, sequelize} = require('../models');
const {Op} = require('sequelize');
const Reservation = require('../models/reservation')(sequelize, Sequelize);
const {NotFoundError} = require('./errors/not_found_error');
const logger = require('logger-ort')(process.env.LOG_IMP);

function addDaysFromDate(date, days) {
  const threeDaysEarlier = new Date();
  threeDaysEarlier.setTime(date.getTime() + (24*60*60*1000) * days);
  return threeDaysEarlier;
}

async function getNumberOfReservationsAtDate(startDate, bookId, organizationId, transaction) {
  const from = addDaysFromDate(startDate, -3);
  const to = addDaysFromDate(startDate, 3);

  return await Reservation.count({
    where: {
      organizationId,
      bookId,
      startDate: {[Op.between]: [from, to]},
      delivered: false,
    },
  }, {transaction});
}

async function create(reservationDTO, apiKey, authorization) {
  const organizationId = (await organizationsService.get(apiKey)).id;
  const book = await booksService.get(reservationDTO.isbn, apiKey);
  const user = await usersService.getMe(authorization, apiKey);

  const transaction = await sequelize.transaction();

  try {
    const startDate = new Date(reservationDTO.startDate);
    const numberReservations = await getNumberOfReservationsAtDate(startDate, book.id, organizationId, transaction);
    if (numberReservations >= book.quantity) {
      logger.log('error', `Failed creating reservation for user ${user.id} and book ${book.id} because no more books`);
      throw new ClientSideError('There are no available copies of the book at the specified date');
    }

    if (await penaltiesService.hasPenalty(user.id, transaction)) {
      logger.log('error', `Failed creating reservation for user with id ${user.id} because it has a penalty`);
      throw new ClientSideError('User has a penalty and cannot make any reservations for now');
    }

    const reservation = await Reservation.create({
      ...reservationDTO,
      bookId: book.id,
      organizationId,
      userId: user.id,
      username: user.name,
      delivered: false,
    }, {transaction});

    await booksService.incrementReservations(book.isbn, apiKey, authorization);

    await transaction.commit();
    logger.log('info', `Created reservation with id ${reservation.id}`);
    return reservation;
  } catch (error) {
    await transaction.rollback();
    throw error;
  }
}

async function rangeQuery(startDate, endDate, page, limit, isbn, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;
  const bookId = (await booksService.get(isbn, apiKey)).id;

  return await Reservation.findAll({
    where: {
      organizationId,
      bookId,
      startDate: {
        [Op.between]: [
          addDaysFromDate(new Date(startDate), -3),
          new Date(endDate),
        ],
      },
    },
    limit,
    offset: limit*(page-1),
  });
}

async function getUserReservations(status, page, limit, userId, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;
  let options = {};

  if (status == 'next') {
    options = {
      startDate: {[Op.gt]: new Date()},
    };
  } else if (status == 'active') {
    options = {
      startDate: {
        [Op.between]: [
          addDaysFromDate(new Date(), -3),
          new Date(),
        ],
      },
      delivered: false,
    };
  } else if (status == 'finished') {
    options = {
      startDate: {[Op.lte]: new Date()},
      delivered: true,
    };
  } else if (status == 'pending') {
    options = {
      startDate: {[Op.lte]: addDaysFromDate(new Date(), -3)},
      delivered: false,
    };
  }

  return await Reservation.findAll({
    where: {organizationId, userId, ...options},
    limit,
    offset: limit*(page-1),
  });
}

async function getBooksReservations(isbn, username, page, limit, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;
  const bookId = (await booksService.get(isbn, apiKey)).id;

  return await Reservation.findAll({
    where: {organizationId, bookId, username: {[Op.like]: `%${username}%`}},
    limit,
    offset: limit*(page-1),
  });
}

async function returnBook(reservationId, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  const [numberOfUpdates, updatedBooks] = await Reservation.update(
      {delivered: true},
      {where: {id: reservationId, organizationId, delivered: false}, returning: true},
  );

  if (numberOfUpdates == 0) {
    throw new NotFoundError('Undelivered book with this ISBN does not exist');
  }

  return updatedBooks[0];
}

async function getNotifiable() {
  const start = addDaysFromDate(new Date(), -2);
  start.setUTCHours(0, 0, 0, 0);

  return await Reservation.findAll({
    where: {
      startDate: start,
      delivered: false,
    },
  });
}

module.exports = {create, rangeQuery, getUserReservations, getBooksReservations, returnBook, getNotifiable};
