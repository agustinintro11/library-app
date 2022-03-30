const {ClientSideError} = require('../errors/client_side_error');
const {TimeoutError} = require('../errors/timeout_error');
const axios = require('axios');
const {
  BASE_USERS_SERVICE_URL,
  SESSIONS_BASE,
  TIMEOUT
} = require('../constants');

async function logIn(email, password, apiKey) {
  const url = `${BASE_USERS_SERVICE_URL}${SESSIONS_BASE}`
  const body = {email, password};
  const headers = {'api-key': apiKey};
  try {
    const response = await axios.post(url, body, {headers, TIMEOUT});
    return response.headers.authorization;
  } catch (error) {
    if (error.code == 'ECONNABORTED') {
      throw new TimeoutError(`Reached timeout of ${TIMEOUT}ms`);
    }
    if (error.response && error.response.status == 401) {
      throw new ClientSideError('Credentials are not valid');
    }
    throw error;
  }
}

module.exports = {logIn};
