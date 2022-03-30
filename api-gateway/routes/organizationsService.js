const express = require('express');
const router = express.Router();
const organizationsServiceController = require('../controllers/organizationsService_controller.js');
const jwt = require('express-jwt');
const requestHandler = require('./requestHandler');

router.post('/organizations/renew',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(organizationsServiceController.renewApiKey),
);

router.get('/organizations/:name/apiKey',
    requestHandler(organizationsServiceController.getApiKey),
);

module.exports = router;
