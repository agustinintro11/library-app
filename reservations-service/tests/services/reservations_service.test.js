jest.mock('../../services/books_service');
jest.mock('../../services/organizations_service');
jest.mock('../../services/users_service');

jest.mock('logger-ort');
jest.mock('newrelic');
const reservationsService = require('../../services/reservations_service');
const db = require('../../models');
const Reservation = require('../../models/reservation')(db.sequelize, db.Sequelize);
const app = require('../../app');

describe('get users reservation', () => {
  let reservations;
  let nextReservations;
  let activeReservations;
  let finishedReservations;
  let pendingReservations;
  let status;
  let page;
  let limit;
  let userId;
  let apiKey;

  beforeAll(async () => {
    await db.sequelize.sync({force: true});
  });

  afterAll(async () => {
    await app.close();
  });

  beforeEach(async () => {
    status = 'active';
    page = 1;
    limit = 20;
    userId = 1;
    apiKey = 'abc';

    const now = new Date();
    const yesterday = new Date(now.getTime() - 86400000);
    const tomorrow = new Date(now.getTime() + 86400000);
    const fiveDaysBefore = new Date(now.getTime() - 5*86400000);

    nextReservations = [
      {id: 1, organizationId: 3, bookId: 1, userId, username: 'abc', startDate: tomorrow, delivered: false},
      {id: 2, organizationId: 3, bookId: 2, userId, username: 'abc', startDate: tomorrow, delivered: false},
    ];
    activeReservations = [
      {id: 3, organizationId: 3, bookId: 1, userId, username: 'abc', startDate: yesterday, delivered: false},
      {id: 4, organizationId: 3, bookId: 2, userId, username: 'abc', startDate: yesterday, delivered: false},
    ];
    finishedReservations = [
      {id: 5, organizationId: 3, bookId: 1, userId, username: 'abc', startDate: yesterday, delivered: true},
      {id: 6, organizationId: 3, bookId: 2, userId, username: 'abc', startDate: yesterday, delivered: true},
    ];
    pendingReservations = [
      {id: 7, organizationId: 3, bookId: 1, userId, username: 'abc', startDate: fiveDaysBefore, delivered: false},
      {id: 8, organizationId: 3, bookId: 2, userId, username: 'abc', startDate: fiveDaysBefore, delivered: false},
    ];

    reservations = [].concat(nextReservations, activeReservations, finishedReservations, pendingReservations);

    for (const reservation of reservations) {
      await Reservation.create(reservation);
    }
  });

  afterEach(async () => {
    Reservation.destroy({where: {}, truncate: true});
  });

  test('gets reservations of user', async () => {
    status = null;
    const serviceReservations = await reservationsService.getUserReservations(status, page, limit, userId, apiKey);
    expect(serviceReservations.map((x) => x.id)).toEqual(reservations.map((x) => x.id));
  });

  test('gets next reservations of user', async () => {
    status = 'next';
    const serviceReservations = await reservationsService.getUserReservations(status, page, limit, userId, apiKey);
    expect(serviceReservations.map((x) => x.id)).toEqual(nextReservations.map((x) => x.id));
  });

  test('gets active reservations of user', async () => {
    status = 'active';
    const serviceReservations = await reservationsService.getUserReservations(status, page, limit, userId, apiKey);
    expect(serviceReservations.map((x) => x.id)).toEqual(activeReservations.map((x) => x.id));
  });

  test('gets finished reservations of user', async () => {
    status = 'finished';
    const serviceReservations = await reservationsService.getUserReservations(status, page, limit, userId, apiKey);
    expect(serviceReservations.map((x) => x.id)).toEqual(finishedReservations.map((x) => x.id));
  });

  test('gets pending reservations of user', async () => {
    status = 'pending';
    const serviceReservations = await reservationsService.getUserReservations(status, page, limit, userId, apiKey);
    expect(serviceReservations.map((x) => x.id)).toEqual(pendingReservations.map((x) => x.id));
  });
});

