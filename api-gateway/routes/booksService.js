const express = require('express');
const router = express.Router();
const booksServiceController = require('../controllers/booksService_controller.js');
const jwt = require('express-jwt');
const requestHandler = require('./requestHandler');

router.post('/books',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(booksServiceController.create),
);

router.put('/books/:isbn',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(booksServiceController.update),
);

router.delete('/books/:isbn',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(booksServiceController.deleteOne),
);

router.get('/books',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    requestHandler(booksServiceController.getAllOrgBooks),
);

router.get('/books/top5',
    requestHandler(booksServiceController.top5),
);

module.exports = router;
