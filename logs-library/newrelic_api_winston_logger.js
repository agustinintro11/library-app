const Transport = require('winston-transport');
const {NewRelicApiLogger} = require('./newrelic_api_logger');
const newrelic = require('newrelic');

class NewRelicApiWinstonLogger extends Transport {
  constructor(options) {
      options = {...options, newrelic, app: 'LibraryDb', env: process.env.ENV, logger: 'winston'};
    super(options);
    this.logger = new NewRelicApiLogger(options);
  }

  log(info, callback) {
    setImmediate(() => this.emit('logged', info));

    const {level, message, ...meta} = info;

    this.logger.log(level, message, meta);

    callback();
  }
};

module.exports = {LoggerTransport: NewRelicApiWinstonLogger};

