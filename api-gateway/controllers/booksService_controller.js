const forwarder = require('./request_forwarder');
const baseUrl = `${process.env.BOOKS_SERVICE_URL}`;

async function create(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }
  const apiKey = request.headers['api-key'];
  if (!apiKey) {
    return response.status(400).send({error: 'Api key is missing'});
  }

  await forwarder.forward(request, response, `${baseUrl}/books`);
}

async function update(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }
  const apiKey = request.headers['api-key'];
  if (!apiKey) {
    return response.status(400).send({error: 'Api key is missing'});
  }
  await forwarder.forward(request, response, `${baseUrl}/books/${request.params.isbn}`);
}

async function deleteOne(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }
  const apiKey = request.headers['api-key'];
  if (!apiKey) {
    return response.status(400).send({error: 'Api key is missing'});
  }

  await forwarder.forward(request, response, `${baseUrl}/books/${request.params.isbn}`);
}

async function getAllOrgBooks(request, response, next) {
  if (!request.user) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const apiKey = request.headers['api-key'];
  if (!apiKey) {
    return response.status(400).send({error: 'Api key is missing'});
  }

  await forwarder.forward(request, response, `${baseUrl}/books`);
}

async function top5(request, response, next) {
  const apiKey = request.headers['api-key'];
  if (!apiKey) {
    return response.status(400).send({error: 'Api key is missing'});
  }

  await forwarder.forward(request, response, `${baseUrl}/books/top5`);
}

module.exports = {create, update, deleteOne, top5, getAllOrgBooks};
