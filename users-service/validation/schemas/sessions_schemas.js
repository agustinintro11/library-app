const Joi = require('joi');

const createLoginSchema = Joi.object().keys({
  email: Joi.string().email().required(),
  password: Joi.string().required(),
});

module.exports = {createLoginSchema};
