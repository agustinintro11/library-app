const Joi = require('joi');

const organizationSchema = Joi.object().keys({
  'organizationName': Joi.string().required(),
}).unknown();

module.exports = {organizationSchema};

