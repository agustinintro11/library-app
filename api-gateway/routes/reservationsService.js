const express = require('express');
const router = express.Router();
const reservationsServiceController = require('../controllers/reservationsService_controller');
const jwt = require('express-jwt');
const requestHandler = require('./requestHandler');

router.post('/reservations',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(reservationsServiceController.create),
);

router.get('/reservations/:isbn/rangeQuery',
    requestHandler(reservationsServiceController.rangeQuery),
);

router.get('/reservations/me',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(reservationsServiceController.getUserReservations),
);

router.get('/reservations',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(reservationsServiceController.getBookReservations),
);

router.patch('/reservations/:id/return',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(reservationsServiceController.returnBook),
);
module.exports = router;
