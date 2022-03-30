const forwarder = require('./request_forwarder');
const baseUrl = `${process.env.USERS_SERVICE_URL}`;

async function createInvitation(request, response, next) {
  if (!request.user || !request.user.isAdmin) {
    return response.status(401).send({error: 'Unauthorized'});
  }

  await forwarder.forward(request, response, `${baseUrl}/invitations`);
}

async function createSession(request, response, next) {
  await forwarder.forward(request, response, `${baseUrl}/sessions`);
}

async function createUser(request, response, next) {
  await forwarder.forward(request, response, `${baseUrl}/users`);
}

async function createWithInvitation(request, response, next) {
  await forwarder.forward(request, response, `${baseUrl}/users/${request.params.invitationId}`);
}

async function getMe(request, response, next) {
  await forwarder.forward(request, response, `${baseUrl}/users/me`);
}

module.exports = {createInvitation, createSession, createUser, createWithInvitation, getMe};
