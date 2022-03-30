const Joi = require('joi');

const createInvitationsSchema = Joi.object().keys({
  email: Joi.string().email().required(),
  isAdmin: Joi.bool().required(),
});

module.exports = {createInvitationsSchema};

