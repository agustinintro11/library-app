const {Sequelize, sequelize} = require('../models');
const User = require('../models/user')(sequelize, Sequelize);
const Invitation = require('../models/invitation')(sequelize, Sequelize);
const {ClientSideError} = require('./errors/client_side_error');
const {NotFoundError} = require('./errors/not_found_error');
const organizationsService = require('./organizations_service');
const logger = require('logger-ort')(process.env.LOG_IMP);

async function createAdmin(user, organizationName) {
  const organization = await organizationsService.create(organizationName);

  user.isAdmin = true;
  user.organizationId = organization.id;

  const createdUser = (await User.create(user)).toJSON();

  logger.log('info', `Created admin ${user.id} and organization ${organization.id}`);
  return {user: createdUser, organization};
}

async function createWithInvitation(user, invitationId, apiKey) {
  const organization = await organizationsService.get(apiKey);

  const invitation = await Invitation.findByPk(invitationId);

  if (!invitation || invitation.email != user.email) {
    throw new NotFoundError('Invitation does not exist');
  }

  if (await User.findOne({where: {organizationId: organization.id, email: invitation.email}})) {
    throw new ClientSideError('User with this email already exists');
  }

  user.isAdmin = invitation.isAdmin;
  user.organizationId = organization.id;
  logger.log('info', `Creating user after invitation ${invitation.id}`);
  return (await User.create(user)).toJSON();
}

async function getMe(userId, apiKey) {
  const organization = await organizationsService.get(apiKey);

  const user = await User.findByPk(userId);

  if (!user || user.organizationId != organization.id) {
    logger.log('info', `User that does not exist tied to get itself`);
    throw new NotFoundError('User does not exist');
  }

  logger.log('info', `User with id ${user.id} got itself`);
  return user;
}

async function getById(id) {
  const user = await User.findByPk(id);

  if (!user) {
    throw new NotFoundError('User does not exist');
  }

  return user;
}

module.exports = {createAdmin, createWithInvitation, getMe, getById};

