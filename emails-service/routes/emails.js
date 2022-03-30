const express = require('express');
const router = express.Router();
const {celebrate, Segments} = require('celebrate');
const {createEmailSchema} = require('../validation/schemas/emails_schemas');
const emailsController = require('../controllers/emails_controller');
const requestHandler = require('./request_handler');

router.post('/',
    celebrate({
      [Segments.BODY]: createEmailSchema,
    }),
    requestHandler(emailsController.create),
);

module.exports = router;
