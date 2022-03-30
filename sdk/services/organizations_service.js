const {NotFoundError} = require('../errors/not_found_error');
const {TimeoutError} = require('../errors/timeout_error');
const {
  BASE_ORGANIZATIONS_SERVICE_URL,
  ORGANIZATIONS_BASE,
  API_KEY_BY_NAME_SUFFIX,
  TIMEOUT
} = require('../constants');
const axios = require('axios');

async function getApiKey(name) {
  const url = `${BASE_ORGANIZATIONS_SERVICE_URL}`+
      `${ORGANIZATIONS_BASE}/${name}${API_KEY_BY_NAME_SUFFIX}`
  try {
    const response = await axios.get(url, {timeout: TIMEOUT});
    return response.data;
  } catch (error) {
    if (error.code == 'ECONNABORTED') {
      throw new TimeoutError(`Reached timeout of ${TIMEOUT}ms`);
    }
    if (error.response && error.response.status == 404) {
      throw new NotFoundError('Organization not found');
    }
    throw error;
  }
}

module.exports = {getApiKey};
