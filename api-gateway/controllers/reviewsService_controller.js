const forwarder = require('./request_forwarder');
const baseUrl = `${process.env.REVIEWS_SERVICE_URL}`;

async function create(request, response, next) {
  if (!request.user || request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await forwarder.forward(request, response, `${baseUrl}/reviews`);
}

async function get(request, response, next) {
  if (!request.user) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await forwarder.forward(request, response, `${baseUrl}/reviews`);
}

async function deleteOne(request, response, next) {
  if (!request.user || request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await forwarder.forward(request, response, `${baseUrl}/reviews/${request.params.reviewId}`);
}

module.exports = {create, get, deleteOne};
