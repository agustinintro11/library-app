require('newrelic');
const express = require('express');
require('dotenv').config();

const reservationsNotificationsRoutes = require('./routes/reservations_notifications_routes');

const app = express();

app.use(express.json());
app.use(express.urlencoded({extended: false}));

app.use('/reservationNotifications', reservationsNotificationsRoutes);

app.use(function(req, res, next) {
  res.status(404).send({error: 'Can\'t find this page'});
});

const logger = require('logger-ort')(process.env.LOG_IMP);

app.use(function(err, req, res, next) {
  if (err.name == 'ClientSideError') {
    return res.status(400).send({error: err.message});
  } else if (err.name == 'NotFoundError') {
    return res.status(404).send({error: err.message});
  } else if (err.name == 'LibraryUnauthorizedError' || err.name == 'UnauthorizedError') {
    return res.status(401).send({error: 'Unauthorized'});
  } else if (err.name == 'NotUniqueError') {
    return res.status(409).send({error: err.message});
  } else if (process.env.ENV == 'DEV') {
    const logger = require('logger-ort')(process.env.LOG_IMP);
    return res.status(500).send({error: err.message, stack: err.stack});
  } else {
    const logger = require('logger-ort')(process.env.LOG_IMP);
    return res.status(500).send({error: 'Something went wrong'});
  }
});

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
