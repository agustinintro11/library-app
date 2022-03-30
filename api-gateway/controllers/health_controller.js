async function get(request, response, next) {
  response.status(200).send();
}

module.exports = {get};
