const Joi = require('joi');

const createEmailSchema = Joi.object().keys({
  email: Joi.string().email().required(),
  template: Joi.string().required(),
  templateOptions: Joi.object().required(),
});

module.exports = {createEmailSchema};

