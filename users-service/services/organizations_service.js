const {NotFoundError} = require('./errors/not_found_error');
const {NotUniqueError} = require('./errors/not_unique_error');
const logger = require('logger-ort')(process.env.LOG_IMP);
const axios = require('axios');

async function get(apiKey) {
  headers = {
    'api-key': apiKey,
  };

  try {
    logger.log('info', `Getting organization by apiKey to endpoint ${process.env.ORGANIZATION_SERVICE_URL}/organizations`);
    const response = await axios.get(`${process.env.ORGANIZATION_SERVICE_URL}/organizations`, {headers});
    return response.data;
  } catch (err) {
    if (err.response && err.response.status == 404) {
      throw new NotFoundError('Organization not found');
    }
    logger.log('error', `Getting organization by apiKey to endpoint ${process.env.ORGANIZATION_SERVICE_URL}/organizations`);
    throw err;
  }
}

async function create(organizationName, headers) {
  try {
    logger.log('info', `Creating organization to endpoint ${process.env.ORGANIZATION_SERVICE_URL}/organizations`);
    const response = await axios.post(`${process.env.ORGANIZATION_SERVICE_URL}/organizations`,
        {organizationName},
        {headers},
    );

    return response.data;
  } catch (err) {
    if (err.response && err.response.status == 409) {
      throw new NotUniqueError(err.response.data.error);
    }
    logger.log('error', `Error on creating organization to endpoint ${process.env.ORGANIZATION_SERVICE_URL}/organizations`);
    throw err;
  }
}


module.exports = {create, get};
