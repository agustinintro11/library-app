const reservationsController = require('../../controllers/reservations_controller');
const reservationsService = require('../../services/reservations_service');

describe('get reservations of user', () => {
  let request;
  let response;
  let next;
  let reservations;
  beforeEach(() => {
    reservations = [
      {organizationId: 3, bookId: 1, userId: 1, username: 'abc', startDate: new Date(), delivered: false},
      {organizationId: 3, bookId: 2, userId: 1, username: 'abc', startDate: new Date(), delivered: false},
    ];
    request = {
      query: {},
      user: {userId: 123, isAdmin: false},
      headers: {'api-key': 123},
    };
    response = {
      status: jest.fn().mockReturnThis(),
      send: jest.fn().mockReturnThis(),
    };
    next = {};
  });

  test('returns 200 when called correctly', async () => {
    jest.spyOn(reservationsService, 'getUserReservations').mockResolvedValueOnce(reservations);

    await reservationsController.getUserReservations(request, response, next);

    expect(response.status).toBeCalledWith(200);
    expect(response.send).toBeCalledWith(reservations);
  });

  test('returns 401 if user is admin', async () => {
    request.user.isAdmin = true;
    jest.spyOn(reservationsService, 'getUserReservations').mockResolvedValueOnce(reservations);

    await reservationsController.getUserReservations(request, response, next);

    expect(response.status).toBeCalledWith(401);
  });
});

