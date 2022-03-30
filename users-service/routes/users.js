const express = require('express');
const router = express.Router();
const usersController = require('../controllers/users_controller');
const requestHandler = require('./requestHandler');
const {celebrate, Segments} = require('celebrate');
const {createUserSchema,
  createUserWithInvitationSchema,
  createUserWithInvitationParamsSchema,
} = require('../validation/schemas/users_schemas');
const {apiKeySchema} = require('../validation/schemas/header_schemas');
const jwt = require('express-jwt');

router.post('/',
    celebrate({
      [Segments.BODY]: createUserSchema,
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(usersController.create),
);

router.post('/:invitationId',
    celebrate({
      [Segments.BODY]: createUserWithInvitationSchema,
      [Segments.PARAMS]: createUserWithInvitationParamsSchema,
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(usersController.createWithInvitation),
);

router.get('/me',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(usersController.getMe),
);

router.get('/:id',
    requestHandler(usersController.getUser),
);

module.exports = router;
