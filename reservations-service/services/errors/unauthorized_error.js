function UnauthorizedError() {
  // JWT library has an error with the same name, to differentiate it
  // it is prefixed with Library
  this.name = 'LibraryUnauthorizedError';
}

module.exports = {UnauthorizedError};
