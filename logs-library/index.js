const winston = require('winston');

module.exports = function logger(implementation){
  const availableImplementations = ['newrelic']

  if (!availableImplementations.includes(implementation)) {
    throw new Error(`Logger ${implementation} is not supported`);
  }

  const {LoggerTransport} = require(`./${implementation}_api_winston_logger`);

  return winston.createLogger({
    level: 'info',
    format: winston.format.json(),
    transports: [new winston.transports.Console(), new LoggerTransport()],
  });
}
