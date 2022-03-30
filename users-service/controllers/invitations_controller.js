const invitationsService = require('../services/invitations_service');

async function create(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const headers = {
    'authorization': request.headers['authorization'],
    'api-key': request.headers['api-key'],
  };
  const invitation = await invitationsService.create(request.body, headers);

  response.status(201).send(invitation);
}

module.exports = {create};
