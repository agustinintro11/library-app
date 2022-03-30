const express = require('express');
const router = express.Router();
const sessionsController = require('../controllers/sessions_controller');
const requestHandler = require('./requestHandler');
const {celebrate, Segments} = require('celebrate');
const {createLoginSchema} = require('../validation/schemas/sessions_schemas');
const {apiKeySchema} = require('../validation/schemas/header_schemas');

router.post('/',
    celebrate({
      [Segments.BODY]: createLoginSchema,
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(sessionsController.create),
);

module.exports = router;
