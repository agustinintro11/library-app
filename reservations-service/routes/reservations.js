const express = require('express');
const router = express.Router();
const reservationsController = require('../controllers/reservations_controller.js');
const jwt = require('express-jwt');
const requestHandler = require('./requestHandler');
const {celebrate, Segments} = require('celebrate');
const {
  createReservationSchema,
  rangeQuerySchema,
  getUserReservationsSchema,
  getBooksReservationsSchema,
} = require('../validation/schemas/reservations_schemas');
const {apiKeySchema} = require('../validation/schemas/header_schemas');

router.post('/',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.BODY]: createReservationSchema,
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(reservationsController.create),
);

router.get('/:isbn/rangeQuery',
    celebrate({
      [Segments.QUERY]: rangeQuerySchema,
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(reservationsController.rangeQuery),
);

router.get('/me',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
      [Segments.QUERY]: getUserReservationsSchema,
    }),
    reservationsController.getUserReservations,
);

router.get('/',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
      [Segments.QUERY]: getBooksReservationsSchema,
    }),
    requestHandler(reservationsController.getBooksReservations),
);

router.patch('/:reservationId/return',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(reservationsController.returnBook),
);

router.get('/notifiable',
    requestHandler(reservationsController.getNotifiable),
);

module.exports = router;
