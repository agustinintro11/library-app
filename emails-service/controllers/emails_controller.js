const emailsService = require('../services/emails_service');

async function create(request, response, _next) {
  const {email, template, templateOptions} = request.body;
  await emailsService.send(email, template, templateOptions);
  response.status(204).send();
}

module.exports = {create};
