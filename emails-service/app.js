require('newrelic');
const express = require('express');
require('dotenv').config();

const emailRouter = require('./routes/emails');
const {errors} = require('celebrate');

const app = express();

const logger = require('logger-ort')(process.env.LOG_IMP);

app.use(express.json());
app.use(express.urlencoded({extended: false}));

app.use('/emails', emailRouter);

app.use(function(req, res, next) {
  res.status(404).send({error: 'Can\'t find this page'});
});

app.use(function(err, req, res, next) {
  if (err.name == 'ClientSideError') {
    return res.status(400).send({error: err.message});
  } else if (err.name == 'UnauthorizedError') {
    return res.status(401).send({error: err.message});
  } else if (process.env.ENV == 'DEV') {
    logger.log('error', `Unexpect error ocurred ${err.message}, ${err.stack}`);
    return res.status(500).send({error: err.message, stack: err.stack});
  } else {
    logger.log('error', `Unexpect error ocurred ${err.message}, ${err.stack}`);
    return res.status(500).send({error: 'Something went wrong'});
  }
});

app.use(errors());

// handle graceful shutdown
process.on('SIGTERM', () => {
  console.info('SIGTERM signal received.');
  server.close(() => {
    console.log('Http server closed.');
    process.exit(0);
  });
});

app.listen(process.env.PORT, () => {
  console.log(`Server listenning on port ${process.env.PORT}`);
});
