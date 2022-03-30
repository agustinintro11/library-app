const sessionsService = require('../services/sessions_service.js');

async function create(request, response, next) {
  const token = await sessionsService.create(request.body, request.headers['api-key']);

  response.setHeader('Authorization', `Bearer ${token}`).status(204).send();
}

module.exports = {create};
