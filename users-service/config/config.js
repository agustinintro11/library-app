require('dotenv');

module.exports = {
  'development': {
    'username': process.env.DB_USER,
    'password': process.env.DB_PASSWORD,
    'database': process.env.DB_NAME,
    'host': process.env.DB_HOST,
    'dialect': 'postgres',
  },
  'test': {
    'username': process.env.DB_TEST_USER,
    'password': process.env.DB_TEST_PASSWORD,
    'database': process.env.DB_TEST_NAME,
    'host': process.env.DB_TEST_HOST,
    'dialect': 'postgres',
  },
  'production': {
    'username': process.env.DB_USER,
    'password': process.env.DB_PASSWORD,
    'database': process.env.DB_NAME,
    'host': process.env.DB_HOST,
    'dialect': 'postgres',
  },
};

