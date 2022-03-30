const axios = require('axios');
const {TimeoutError} = require('../errors/timeout_error');
const {ClientSideError} = require('../errors/client_side_error');
const {
  BASE_RESERVATIONS_SERVICE_URL,
  RESERVATIONS_BASE,
  RANGE_QUERY_SUFFIX,
  TIMEOUT
} = require('../constants');

async function rangeQuery(isbn, startDate, endDate, page, limit, apiKey, authorization) {
  const url = `${BASE_RESERVATIONS_SERVICE_URL}`+
      `${RESERVATIONS_BASE}/${isbn}${RANGE_QUERY_SUFFIX}`;
  const params = {page, limit, startDate, endDate};
  const headers = {'api-key': apiKey, authorization};
  try {
    const response = await axios.get(url, {params, headers, timeout: TIMEOUT});
    return response.data;
  } catch (error) {
    if (error.code == 'ECONNABORTED') {
      throw new TimeoutError(`Reached timeout of ${TIMEOUT}ms`);
    }
    if (error.code == 401) {
      throw new ClientSideError('Credentials are not valid');
    }
    throw error;
  }
}

module.exports = {rangeQuery};
