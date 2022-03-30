const express = require('express');
const router = express.Router();
const booksController = require('../controllers/books_controller.js');
const jwt = require('express-jwt');
const requestHandler = require('./requestHandler');
const {celebrate, Segments} = require('celebrate');
const {createBookSchema, updateBookSchema, getAllBooksSchema} = require('../validation/schemas/books_schemas');
const {apiKeySchema} = require('../validation/schemas/header_schemas');

router.post('/',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.BODY]: createBookSchema,
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(booksController.create),
);

router.put('/:isbn',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.BODY]: updateBookSchema,
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(booksController.update),
);

router.delete('/:isbn',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(booksController.deleteOne),
);

router.get('/',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
      [Segments.QUERY]: getAllBooksSchema,
    }),
    requestHandler(booksController.getAll),
);

router.get('/top5',
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(booksController.top5),
);


router.get('/:isbn',
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(booksController.get),
);

router.post('/:isbn/incrementReservations',
    jwt({secret: process.env.AUTH_SECRET, algorithms: ['HS256']}),
    celebrate({
      [Segments.HEADERS]: apiKeySchema,
    }),
    requestHandler(booksController.incrementReservations),
);

module.exports = router;

