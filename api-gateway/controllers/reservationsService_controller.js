const forwarder = require('./request_forwarder');
const baseUrl = `${process.env.RESERVATIONS_SERVICE_URL}`;

async function create(request, response, next) {
  if (!request.user || request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await forwarder.forward(request, response, `${baseUrl}/reservations`);
}

async function rangeQuery(request, response, next) {
  await forwarder.forward(request, response, `${baseUrl}/reservations/${request.params.isbn}/rangeQuery`);
}

async function getUserReservations(request, response, next) {
  if (!request.user) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await forwarder.forward(request, response, `${baseUrl}/reservations/me`);
}

async function getBookReservations(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await forwarder.forward(request, response, `${baseUrl}/reservations`);
}

async function returnBook(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await forwarder.forward(request, response, `${baseUrl}/reservations/${request.params.id}/return`);
}

module.exports = {create, rangeQuery, getUserReservations, getBookReservations, returnBook};
