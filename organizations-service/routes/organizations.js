const express = require('express');
const router = express.Router();
const organizationsController = require('../controllers/organizations_controller.js');
const jwt = require('express-jwt');
const requestHandler = require('./requestHandler');
const {organizationSchema} = require('../validation/schemas/organization_schema.js');
const {apiKeySchema} = require('../validation/schemas/header_schemas.js');
const {celebrate, Segments} = require('celebrate');

router.post('/',
    celebrate({
      [Segments.BODY]: organizationSchema,
    }),
    requestHandler(organizationsController.create),
);

router.post('/renew',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(organizationsController.renewApiKey),
);

router.get('/:name/apiKey',
    requestHandler(organizationsController.getApiKey),
);

router.get('/',
    requestHandler(organizationsController.getOrganization),
);

module.exports = router;
