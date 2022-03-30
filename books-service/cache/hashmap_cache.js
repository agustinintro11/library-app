class Data {
  constructor(value, durationInMs) {
    this.value = value;
    this.timestampEnd = new Date().getTime() + durationInMs;
  }
};

const nonExpiringDuration = 1000*60*60*24*365*10;

class ConnectionSingleton {
  static instance;

  constructor() {
    this.connection = {};
  }

  static getInstance() {
    if (!this.instance) {
      this.instance = new ConnectionSingleton();
    }
    return this.instance;
  }
};

function connection() {
  return ConnectionSingleton.getInstance().connection;
}

function get(key) {
  const data = connection()[key];
  if (!data) {
    return null;
  }

  if (data.timestampEnd < new Date().getTime()) {
    connection()[key] = null;
    return null;
  }

  return connection()[key].value;
}

function exists(key) {
  return !!get(key);
}

function set(key, value, ttl=null) {
  if (ttl != null) {
    connection()[key] = new Data(value, ttl*1000);
  } else {
    connection()[key] = new Data(value, nonExpiringDuration);
  }
}

module.exports = {connection, get, set, exists};

