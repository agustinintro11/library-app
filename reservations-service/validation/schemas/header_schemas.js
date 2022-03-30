const Joi = require('joi');

const apiKeySchema = Joi.object().keys({
  'api-key': Joi.string().required(),
}).unknown();

module.exports = {apiKeySchema};
