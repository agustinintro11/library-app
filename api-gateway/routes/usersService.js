const express = require('express');
const router = express.Router();
const usersServiceController = require('../controllers/usersService_controller.js');
const jwt = require('express-jwt');
const requestHandler = require('./requestHandler');

router.post('/invitations',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(usersServiceController.createInvitation),
);

router.post('/sessions',
    requestHandler(usersServiceController.createSession),
);

router.post('/users',
    requestHandler(usersServiceController.createUser),
);

router.post('/users/:invitationId',
    requestHandler(usersServiceController.createWithInvitation),
);

router.get('/users/me',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(usersServiceController.getMe),
);

module.exports = router;
