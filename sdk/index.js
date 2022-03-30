require('dotenv').config();
const organizationsService = require('./services/organizations_service');
const usersService = require('./services/users_service');
const reservationsService = require('./services/reservations_service');

class ReservationsSdk {  
  constructor(orgName, username, password) {
    this.orgName = orgName;
    this.username = username;
    this.password = password;
  }

  async init() {
    this.apiKey = (await organizationsService.getApiKey(this.orgName)).apiKey;
    this.authorization = await usersService.logIn(this.username, this.password, this.apiKey);
  }

  async rangeQuery(isbn, startDate, endDate, page, limit) {
    return await reservationsService.rangeQuery(isbn, startDate, endDate, page, limit, this.apiKey, this.authorization);
  }
}

module.exports = ReservationsSdk;
