'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.addIndex(
      'Books',
      ['organizationId', 'deleted', 'isbn'],
      {name: 'books_isbn_index'}
    );
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.removeIndex('Books', 'books_isbn_index');
  }
};
