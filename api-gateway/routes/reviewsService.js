const express = require('express');
const router = express.Router();
const reviewsServiceController = require('../controllers/reviewsService_controller');
const jwt = require('express-jwt');
const requestHandler = require('./requestHandler');

router.post('/reviews',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(reviewsServiceController.create),
);

router.get('/reviews',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(reviewsServiceController.get),
);

router.delete('/reviews/:reviewId',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(reviewsServiceController.deleteOne),
);

module.exports = router;
