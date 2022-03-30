const reservationsService = require('./reservations_service');
const usersService = require('./users_service');
const axios = require('axios');
const logger = require('logger-ort')(process.env.LOG_IMP);

async function createNotifications() {
  const activeReservations = await reservationsService.getAllNotifiable();

  logger.log('info', 'Creating notifications for reservations');
  for (const reservation of activeReservations) {
    sendEmail(reservation);
  }
}

async function sendEmail(reservation) {
  const user = await usersService.get(reservation.userId);

  const params = {
    email: user.email,
    template: 'notification_return_book',
    templateOptions: {username: user.name},
  };

  await axios.post(`${process.env.EMAIL_SERVICE_URL}/emails`, params);
}

module.exports = {createNotifications};

