const {NotFoundError} = require('./errors/not_found_error');
const axios = require('axios');
const logger = require('logger-ort')(process.env.LOG_IMP);

async function get(apiKey) {
  headers = {
    'api-key': apiKey,
  };

  try {
    logger.info(`Getting organization by apiKey to endpoint ${process.env.ORGANIZATION_SERVICE_URL}/organizations`);
    const response = await axios.get(`${process.env.ORGANIZATION_SERVICE_URL}/organizations`, {headers});
    return response.data;
  } catch (err) {
    if (err.response && err.response.status == 404) {
      throw new NotFoundError('Organization not found');
    }
    throw err;
  }
}

module.exports = {get};
