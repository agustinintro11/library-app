const express = require('express');
const router = express.Router();
const invitationsController = require('../controllers/invitations_controller');
const requestHandler = require('./requestHandler');
const {celebrate, Segments} = require('celebrate');
const {createInvitationsSchema} = require('../validation/schemas/invitations_schemas');
const {apiKeySchema} = require('../validation/schemas/header_schemas');
const jwt = require('express-jwt');

router.post('/',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.BODY]: createInvitationsSchema,
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(invitationsController.create),
);

module.exports = router;

