const {Sequelize, sequelize} = require('../models');
const axios = require('axios');
const Invitation = require('../models/invitation')(sequelize, Sequelize);
const organizationsService = require('./organizations_service');
const logger = require('logger-ort')(process.env.LOG_IMP);

async function create(invitation, headers) {
  const organization = await organizationsService.get(headers['api-key']);

  invitation.organizationId = organization.id;

  logger.log('info', `Invitation to ${organization.name} was sent with values ${JSON.stringify(invitation)}`);
  const createdInvitation = (await Invitation.create(invitation)).toJSON();

  await sendInviteEmail(createdInvitation, organization, headers);

  return createdInvitation;
}

async function sendInviteEmail(invitation, organization, headers) {
  const link = `libraryapp.com/signup/${invitation.id}?orgName=${organization.name}&email=${invitation.email}`;
  const params = {
    email: invitation.email,
    template: 'invite_to_organization',
    templateOptions: {
      org: organization.name,
      link,
    },
  };

  logger.log('info', `Sending invite to organization email to ${invitation.email}`);
  await axios.post(`${process.env.EMAIL_SERVICE_URL}/emails`, params, {headers});
}

module.exports = {create};

