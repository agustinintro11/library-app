const {connection, set, get, exists} = require(`./${process.env.CACHE_IMP || 'hashmap'}_cache`);

module.exports = {
  connection, set, get, exists,
};
