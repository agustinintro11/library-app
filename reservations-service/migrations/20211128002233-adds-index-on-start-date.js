'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.addIndex(
      'Reservations',
      ['organizationId', 'bookId', 'startDate'],
      {name: 'reservations_start_date_index'}
    );
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.removeIndex('Reservations', '`reservations_start_date_index`');
  }
};

