const {NotFoundError} = require('./errors/not_found_error');
const logger = require('logger-ort')(process.env.LOG_IMP);
const axios = require('axios');

async function get(isbn, apiKey) {
  const headers = {'api-key': apiKey};

  try {
    logger.log('info', `Getting book to endpoint ${process.env.BOOK_SERVICE_URL}/books/${isbn}`);
    const response = await axios.get(`${process.env.BOOK_SERVICE_URL}/books/${isbn}`, {headers});
    return response.data;
  } catch (err) {
    if (err.response && err.response.status == 404) {
      throw new NotFoundError('Book not found');
    }
    throw err;
  }
}

async function incrementReservations(isbn, apiKey, authorization) {
  const headers = {'api-key': apiKey, authorization};

  try {
    const url = `${process.env.BOOK_SERVICE_URL}/books/${isbn}/incrementReservations`;
    const response = await axios.post(url, {}, {headers});
    return response.data;
  } catch (err) {
    if (err.response && err.response.status == 404) {
      throw new NotFoundError('Book not found');
    }
    throw err;
  }
}

module.exports = {get, incrementReservations};

