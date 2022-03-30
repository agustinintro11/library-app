const express = require('express');
const router = express.Router();
const healthController = require('../controllers/health_controller');
const requestHandler = require('./requestHandler');

router.get('/',
    requestHandler(healthController.get),
);

module.exports = router;
