const Joi = require('joi');

const createReservationSchema = Joi.object().keys({
  isbn: Joi.string().required(),
  startDate: Joi.string().required(),
});

const rangeQuerySchema = Joi.object().keys({
  startDate: Joi.string().required(),
  endDate: Joi.string().required(),
  page: Joi.number().min(1).default(1),
  limit: Joi.number().min(1).max(200).default(20),
});

const getUserReservationsSchema = Joi.object().keys({
  status: Joi.string().valid('next', 'active', 'finished', 'pending'),
  page: Joi.number().min(1).default(1),
  limit: Joi.number().min(1).max(200).default(20),
});

const getBooksReservationsSchema = Joi.object().keys({
  isbn: Joi.string().required(),
  username: Joi.string().default(''),
  page: Joi.number().min(1).default(1),
  limit: Joi.number().min(1).max(200).default(20),
});

module.exports = {
  getBooksReservationsSchema,
  createReservationSchema,
  rangeQuerySchema,
  getUserReservationsSchema,
};
