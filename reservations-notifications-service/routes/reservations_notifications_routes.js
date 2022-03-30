const express = require('express');
const router = express.Router();
const reservationsNotificationsController =
  require('../controllers/reservations_notifications_controller');
const requestHandler = require('./requestHandler');

router.post('/',
    requestHandler(reservationsNotificationsController.createNotifications),
);

module.exports = router;

