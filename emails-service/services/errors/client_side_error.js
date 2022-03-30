module.exports = function ClientSideError(message) {
  this.name = 'ClientSideError';
  this.message = message;
};
