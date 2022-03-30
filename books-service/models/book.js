'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Book extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  };
  Book.init({
    isbn: DataTypes.STRING,
    title: DataTypes.STRING,
    authors: DataTypes.STRING,
    year: DataTypes.INTEGER,
    quantity: DataTypes.INTEGER,
    organizationId: DataTypes.INTEGER,
    timesReserved: DataTypes.INTEGER,
    deleted: DataTypes.BOOLEAN
  }, {
    sequelize,
    modelName: 'Book',
  });
  return Book;
};
