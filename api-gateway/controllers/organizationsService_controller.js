const forwarder = require('./request_forwarder');
const baseUrl = `${process.env.ORGANIZATIONS_SERVICE_URL }`;

async function renewApiKey(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }
  await forwarder.forward(request, response, `${baseUrl}/organizations/renew`);
}

async function getApiKey(request, response, next) {
  await forwarder.forward(request, response, `${baseUrl}/organizations/${request.params.name}/apiKey`);
}

module.exports = {renewApiKey, getApiKey};
