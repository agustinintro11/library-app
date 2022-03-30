'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.addIndex(
      'Books',
      ['organizationId', 'deleted', 'timesReserved'],
      {name: 'books_times_reserved_index'}
    );
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.removeIndex('Books', 'books_times_reserved_index');
  }
};

