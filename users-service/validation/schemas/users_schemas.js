const Joi = require('joi');

const createUserSchema = Joi.object().keys({
  email: Joi.string().email().required(),
  password: Joi.string().required(),
  organization: Joi.string().required(),
  name: Joi.string().required(),
});

const createUserWithInvitationSchema = Joi.object().keys({
  email: Joi.string().email().required(),
  password: Joi.string().required(),
  name: Joi.string().required(),
});

const createUserWithInvitationParamsSchema = Joi.object().keys({
  invitationId: Joi.number().required(),
});

module.exports = {createUserSchema, createUserWithInvitationSchema, createUserWithInvitationParamsSchema};

