const axios = require('axios');
const logger = require('logger-ort')(process.env.LOG_IMP);

async function get(id) {
  try{
    const response = await axios.get(`${process.env.USERS_SERVICE_URL}/users/${id}`);

    return response.data;
  } catch (err) {
    if (err.response && err.response.status == 404) {
      logger.log('warn', `user with id ${id} was not found`);
    } else {
      logger.log('warn', `Something happened when getting user with id ${id}`);
    }
  }
  return null;
}

module.exports = {get};

