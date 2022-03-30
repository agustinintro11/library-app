const booksService = require('../services/books_service.js');

async function create(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const book = await booksService.create(request.body, request.headers['api-key']);

  response.status(201).send(book);
}

async function update(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const book = await booksService.update(request.body, request.params.isbn, request.headers['api-key']);

  if (!book) {
    return response.status(404).send({error: 'Book with this ISBN does not exist for this organization'});
  }

  response.status(200).send(book);
}

async function deleteOne(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await booksService.deleteOne(request.params.isbn, request.headers['api-key']);

  response.status(204).send();
}

async function getAll(request, response, next) {
  if (!request.user) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const {limit, page, filter} = request.query;

  const books = await booksService.getAll(limit, page, filter, request.headers['api-key']);

  response.status(200).send(books);
}

async function get(request, response, next) {
  const book = await booksService.get(request.params.isbn, request.headers['api-key']);

  response.status(200).send(book);
}

async function incrementReservations(request, response, next) {
  if (!request.user) { // TODO: Check if correct
    return response.status(401).send({error: 'Unauthorized'});
  }

  const book = await booksService.incrementReservations(request.params.isbn, request.headers['api-key']);

  response.status(200).send(book);
}

async function top5(request, response, next) {
  const top5 = await booksService.top5(request.headers['api-key']);

  response.status(200).send(top5);
}

module.exports = {create, update, deleteOne, getAll, get, incrementReservations, top5};

