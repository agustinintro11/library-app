const reservationsNotificationsService = require('../services/reservations_notifications_service');

async function createNotifications(request, response, next) {
  await reservationsNotificationsService.createNotifications();

  response.status(204).send();
}

module.exports = {createNotifications};

