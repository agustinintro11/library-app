const reservationsService = require('../services/reservations_service.js');

async function create(request, response, next) {
  if (!request.user || request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const reservation = await reservationsService.create(
      request.body,
      request.headers['api-key'],
      request.headers['authorization'],
  );

  response.status(201).send(reservation);
}

async function rangeQuery(request, response, next) {
  const {startDate, endDate, page, limit} = request.query;

  const reservations = await reservationsService.rangeQuery(
      startDate,
      endDate,
      page,
      limit,
      request.params.isbn,
      request.headers['api-key'],
  );

  response.status(200).send(reservations);
}

async function getUserReservations(request, response, next) {
  if (!request.user || request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const {status, page, limit} = request.query;

  const reservations = await reservationsService.getUserReservations(
      status,
      page,
      limit,
      request.user.userId,
      request.headers['api-key'],
  );

  response.status(200).send(reservations);
}

async function getBooksReservations(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const {isbn, page, limit, username} = request.query;

  const reservations = await reservationsService.getBooksReservations(
      isbn,
      username,
      page,
      limit,
      request.headers['api-key'],
  );

  response.status(200).send(reservations);
}

async function returnBook(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const reservations = await reservationsService.returnBook(
      request.params.reservationId,
      request.headers['api-key'],
  );

  response.status(200).send(reservations);
}

async function getNotifiable(request, response, next) {
  const reservations = await reservationsService.getNotifiable();
  response.status(200).send(reservations);
}

module.exports = {create, rangeQuery, getUserReservations, getBooksReservations, returnBook, getNotifiable};

