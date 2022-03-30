jest.mock('logger-ort');
jest.mock('newrelic');
jest.mock('../../controllers/reservations_controller');
const db = require('../../models');
const request =require('supertest');
const app = require('../../app');
const jwt = require('jsonwebtoken');

describe('get user reservations by status', () => {
  let params;
  let token;

  beforeAll(async () => {
    await db.sequelize.sync({force: true});
  });

  afterAll(async () => {
    await app.close();
  });

  beforeEach(() => {
    token = jwt.sign({userId: 3}, 'EXAMPLE_SECRET_123', {expiresIn: 10000});
    params = {
      status: 'active',
      page: 1,
      limit: 10,
    };
  });

  test('returns 200 when params are correct', async () => {
    const response = await request(app)
        .get('/reservations/me')
        .set('Authorization', `Bearer ${token}`)
        .set('api-key', 'abc')
        .query(params);

    expect(response.status).toEqual(200);
  });

  test('returns 400 if page is a string', async () => {
    params.page = 'abc';
    const response = await request(app)
        .get('/reservations/me')
        .set('Authorization', `Bearer ${token}`)
        .set('api-key', 'abc')
        .query(params);

    expect(response.status).toEqual(400);
    expect(response.body.validation.query.message).toEqual('"page" must be a number');
  });

  test('returns 400 if status is not a valid value', async () => {
    params.status = 'abc';
    const response = await request(app)
        .get('/reservations/me')
        .set('Authorization', `Bearer ${token}`)
        .set('api-key', 'abc')
        .query(params);

    expect(response.status).toEqual(400);
    expect(response.body.validation.query.message).toEqual('"status" must be one of [next, active, finished, pending]');
  });

  test('returns 401 if authorization is not sent', async () => {
    const response = await request(app)
        .get('/reservations/me')
        .set('api-key', 'abc')
        .query(params);

    expect(response.status).toEqual(401);
  });

  test('returns 401 if invalid token is sent', async () => {
    const response = await request(app)
        .get('/reservations/me')
        .set('Authorization', `Bearer abc`)
        .set('api-key', 'abc')
        .query(params);

    expect(response.status).toEqual(401);
  });

  test('returns 400 if api key is sent', async () => {
    const response = await request(app)
        .get('/reservations/me')
        .set('Authorization', `Bearer ${token}`)
        .query(params);

    expect(response.status).toEqual(400);
  });
});

