const axios = require('axios');

async function getMe(authorization, apiKey) {
  const headers = {authorization, 'api-key': apiKey};
  const response = await axios.get(`${process.env.USER_SERVICE_URL}/users/me`, {headers});

  return response.data;
}

module.exports = {getMe};

