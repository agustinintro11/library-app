const {NotFoundError} = require('./errors/not_found_error');
const logger = require('logger-ort')(process.env.LOG_IMP);
const axios = require('axios');

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

async function getByName(name) {
  try {
    const apiKey =
      (await axios.get(`${process.env.ORGANIZATION_SERVICE_URL}/organizations/${name}/apiKey`)).data.apiKey;
    return await get(apiKey);
  } catch (err) {
    if (err.response && err.response.status == 404) {
      throw new NotFoundError('Organization not found');
    }
    throw err;
  }
}

module.exports = {get, getByName};

