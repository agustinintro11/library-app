const axios = require('axios');
async function forward(request, response, url) {
  const headers = {
    'Authorization': request.headers.authorization ? request.headers.authorization : null,
    'api-key': request.headers['api-key'] ? request.headers['api-key'] : null,
  };

  const queryString = new URLSearchParams(request.query).toString();
  const queryParams = queryString ? `?${queryString}` : '';

  const forwardedResponse = await axios({
    method: request.method,
    url: url + queryParams,
    data: request.body,
    headers: headers,
    validateStatus: (status) => true,
  });
  delete forwardedResponse.headers['transfer-encoding'];

  response.status(forwardedResponse.status).header(forwardedResponse.headers).send(forwardedResponse.data);
}

module.exports = {forward};
