const organizationsService = require('../services/organizations_service.js');

async function create(request, response, next) {
  const organization = await organizationsService.create(request.body);

  response.status(201).send(organization);
}

async function renewApiKey(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const apiKey = request.headers['api-key'];
  const organization = await organizationsService.renewApiKey(apiKey);

  response.status(201).send({apiKey: organization.apiKey});
}

async function getApiKey(request, response, next) {
  const apiKey = await organizationsService.getApiKey(request.params.name);
  response.status(200).send({apiKey});
}

async function getOrganization(request, response, next) {
  const apiKey = request.headers['api-key'];
  const organization = await organizationsService.getOrganizationByApiKey(apiKey);

  response.status(200).send(organization);
}


module.exports = {create, renewApiKey, getApiKey, getOrganization};
