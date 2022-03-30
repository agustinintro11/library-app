const {Sequelize, sequelize} = require('../models');
const User = require('../models/user')(sequelize, Sequelize);
const jwt = require('jsonwebtoken');
const organizationsService = require('./organizations_service');
const {UnauthorizedError} = require('./errors/unauthorized_error');
const logger = require('logger-ort')(process.env.LOG_IMP);

async function create(credentials, apiKey) {
  const organizationId = (await organizationsService.get(apiKey)).id;

  const user = await User.findOne({where: {email: credentials.email, organizationId}});

  if (user == null || user.password != credentials.password) {
    throw new UnauthorizedError();
  }

  logger.log('error', `Creating jwt token for ${user.id}`);
  const token = jwt.sign(
      {userId: user.id, isAdmin: user.isAdmin},
      process.env.AUTH_SECRET,
      {expiresIn: process.env.AUTH_TOKEN_EXPIRATION},
  );
  return token;
}

module.exports = {create};
