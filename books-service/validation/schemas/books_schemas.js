const Joi = require('joi');
const ISBN = require('isbn-validate');

const createBookSchema = Joi.object().keys({
  authors: Joi.string().required(),
  title: Joi.string().required(),
  isbn: Joi.string().custom((value, helper) => {
    if (!ISBN.Validate(value)) {
      return helper.message('Isbn is not valid');
    }

    return value;
  }).required(),
  year: Joi.number().required(),
  quantity: Joi.number().min(0).required(),
});

const updateBookSchema = Joi.object().keys({
  authors: Joi.string(),
  title: Joi.string(),
  isbn: Joi.string(),
  year: Joi.number(),
  quantity: Joi.number().min(0),
});

const getAllBooksSchema = Joi.object().keys({
  limit: Joi.number().min(1).max(200).default(20),
  page: Joi.number().min(1).default(1),
  filter: Joi.string().default(''),
});

module.exports = {createBookSchema, updateBookSchema, getAllBooksSchema};

