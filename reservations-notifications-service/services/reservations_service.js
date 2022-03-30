const axios = require('axios');

async function getAllNotifiable() {
  const response = await axios.get(`${process.env.RESERVATIONS_SERVICE_URL}/reservations/notifiable`);

  return response.data;
}

module.exports = {getAllNotifiable};

