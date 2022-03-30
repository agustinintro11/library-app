const usersService = require('../services/users_service');

function createUserOut(user) {
  return {
    id: user.id,
    name: user.name,
    email: user.email,
    isAdmin: user.isAdmin,
  };
}

async function create(request, response, next) {
  const {user, organization} = await usersService.createAdmin(request.body, request.body.organization);

  response.status(201).send({user: createUserOut(user), organization});
}

async function createWithInvitation(request, response, next) {
  const invitationId = parseInt(request.params.invitationId);
  const user = await usersService.createWithInvitation(request.body, invitationId, request.headers['api-key']);

  response.status(201).send(createUserOut(user));
}

async function getMe(request, response, next) {
  if (!request.user) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  const user = await usersService.getMe(request.user.userId, request.headers['api-key']);

  response.status(200).send(createUserOut(user));
}

async function getUser(request, response, next) {
  const {id} = request.params;

  const user = await usersService.getById(id);

  response.status(200).send(user);
}

module.exports = {create, getMe, createWithInvitation, getUser};

