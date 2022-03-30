const {Sequelize, sequelize} = require('../models');
const Reservation = require('../models/reservation')(sequelize, Sequelize);
const {Op} = require('sequelize');

function addDaysFromDate(date, days) {
  const threeDaysEarlier = new Date();
  threeDaysEarlier.setTime(date.getTime() + (24*60*60*1000) * days);
  return threeDaysEarlier;
}

async function hasTwoPendingBooks(userId, transaction) {
  const reservations = await Reservation.findAll({
    where: {
      userId,
      startDate: {[Op.lte]: addDaysFromDate(new Date(), -3)},
      delivered: false,
    },
    limit: 2,
    transaction,
  });

  return reservations.length == 2;
}

async function hasOneMoreThan72hours(userId, transaction) {
  const reservations = await Reservation.findAll({
    where: {
      userId,
      startDate: {[Op.lte]: addDaysFromDate(new Date(), - 3 - 72/24)},
      delivered: false,
    },
    limit: 1,
    transaction,
  });

  return reservations.length == 1;
}

async function hasPenalty(userId, transaction) {
  return await hasTwoPendingBooks(userId, transaction) || await hasOneMoreThan72hours(userId, transaction);
}

module.exports = {hasPenalty};

