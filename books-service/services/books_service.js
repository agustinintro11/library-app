const {Sequelize, sequelize} = require('../models');
const {Op} = require('sequelize');
const Book = require('../models/book')(sequelize, Sequelize);
const organizationsService = require('./organizations_service');
const {NotUniqueError} = require('./errors/not_unique_error');
const {NotFoundError} = require('./errors/not_found_error');
const cache = require('../cache/cache');
const logger = require('logger-ort')(process.env.LOG_IMP);

async function create(book, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  const [createdBook, created] = await Book.findOrCreate({
    where: {organizationId, isbn: book.isbn, deleted: false},
    defaults: {...book, organizationId, deleted: false, timesReserved: 0},
  });
  if (!created) {
    logger.log('error', `Tried creating book with isbn ${book.isbn} in organization ${organizationId}`);
    throw new NotUniqueError('Book with this ISBN was already created for this organization');
  }

  logger.log('info', `Created book with id ${createdBook.id}`);
  return createdBook;
}

async function update(book, isbn, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  const [numberOfUpdates, updatedBooks] = await Book.update(
      book,
      {where: {organizationId, isbn, deleted: false}, returning: true},
  );

  if (numberOfUpdates == 0) {
    throw new NotFoundError('Book with this ISBN does not exist');
  }

  logger.log('info', `Updated book with id ${updatedBooks[0].id}`);
  return updatedBooks[0];
}

async function deleteOne(isbn, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  // TODO: Don't delete books with active bookigns

  const [numberOfUpdates, updatedBooks] = await Book.update(
      {deleted: true},
      {where: {organizationId, isbn, deleted: false}, returning: true},
  );

  if (numberOfUpdates == 0) {
    throw new NotFoundError('Book with this ISBN does not exist');
  }

  logger.log('info', `Deleted book with isbn ${isbn} in organization ${organizationId}`);
  return updatedBooks[0];
}

async function getAll(limit, page, filter, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  const books = await Book.findAll({
    where: {
      organizationId,
      deleted: false,
      [Op.or]: {
        title: {[Op.like]: `%${filter}%`},
        authors: {[Op.like]: `%${filter}%`},
      },
    },
    limit,
    order: [['id', 'ASC']],
    offset: limit*(page-1),
  });

  logger.log('info', `Returned all books from orgnaization ${organizationId}`);
  return books;
}

async function get(isbn, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  const book = await Book.findOne({where: {organizationId, isbn, deleted: false}});

  if (!book) {
    throw new NotFoundError('Book with this ISBN does not exist');
  }

  return book
}

async function incrementReservations(isbn, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  const book = await Book.update({timesReserved: sequelize.literal('"timesReserved" + 1')}, {where: {organizationId, isbn, deleted: false}});

  if (!book) {
    throw new NotFoundError('Book with this ISBN does not exist');
  }

  logger.log('info', `Incremented reservations of book with isbn ${isbn} in organization ${organizationId}`);
  return book
}

async function top5(apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  const cacheKey = `${organizationId}.top5`;
  const cacheValue = await cache.get(cacheKey);
  if (cacheValue) {
    return JSON.parse(cacheValue);
  }

  const top5books = await Book.findAll({
    where: {
      organizationId,
      deleted: false,
    },
    order: [['timesReserved', 'DESC']],
    limit: 5,
  });

  logger.log('info', `Setting cache for top 5 in organization ${organizationId}`);
  await cache.set(cacheKey, JSON.stringify(top5books), 90);

  return top5books;
}

module.exports = {create, update, deleteOne, getAll, get, incrementReservations, top5};

