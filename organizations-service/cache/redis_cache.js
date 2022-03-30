const {createClient} = require('redis');

class ConnectionSingleton {
  static instance;

  constructor() {
    this.connection = createClient({url: process.env.REDIS_URL});
  }

  static async getInstance() {
    if (!this.instance) {
      this.instance = new ConnectionSingleton();
      await this.instance.connection.connect();
    }
    return this.instance;
  }
};

async function connection() {
  return (await ConnectionSingleton.getInstance()).connection;
}

async function get(key) {
  return await (await connection()).get(key);
}

async function exists(key) {
  return await (await connection()).exists(key);
}

async function set(key, value, ttl=null) {
  if (ttl != null) {
    return await (await connection()).set(key, value, {'EX': ttl});
  }
  return await (await connection()).set(key, value);
}

module.exports = {connection, get, set, exists};
