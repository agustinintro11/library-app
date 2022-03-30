require('newrelic');
const express = require('express');

require('dotenv').config();

const indexRouter = require('./routes/index');
const booksRouter = require('./routes/booksService');
const reservationsRouter = require('./routes/reservationsService');
const healthRouter = require('./routes/health');
const organizationsRouter = require('./routes/organizationsService');
const usersRouter = require('./routes/usersService');
const reviewsRouter = require('./routes/reviewsService');

const app = express();

const logger = require('logger-ort')(process.env.LOG_IMP);

app.use(express.json());
app.use(express.urlencoded({extended: false}));

app.use('/', indexRouter);
app.use('/health', healthRouter);
app.use('/books_service', booksRouter);
app.use('/reservations_service', reservationsRouter);
app.use('/organizations_service', organizationsRouter);
app.use('/users_service', usersRouter);
app.use('/reviews_service', reviewsRouter);


app.use(function(req, res, next) {
  res.status(404).send({error: 'Can\'t find this page'});
});

app.use(function(err, req, res, next) {
  if (err.name == 'NotUniqueError') {
    return res.status(422).send({error: err.message});
  } else if (err.name == 'ClientSideError') {
    return res.status(400).send({error: err.message});
  } else if (err.name == 'LibraryUnauthorizedError' || err.name == 'UnauthorizedError') {
    return res.status(401).send({error: 'Unauthorized'});
  } else if (err.name == 'NotFoundError') {
    return res.status(404).send({error: err.message});
  } else if (process.env.ENV == 'DEV') {
    logger.log('error', `Unexpected error ocurred ${err.message}, ${err.stack}`);
    res.status(500).send({error: err.message, stack: err.stack});
  } else {
    logger.log('error', `Unexpected error ocurred ${err.message}, ${err.stack}`);
    res.status(500).send({error: 'Something went wrong'});
  }
});

app.listen(process.env.PORT, () => {
  console.log(`Server listenning on port ${process.env.PORT}`);
});
