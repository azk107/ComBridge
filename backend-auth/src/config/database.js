const mysql = require('mysql2/promise');
const { getConfig } = require('./env');

const config = getConfig();

const createPool = () => {
  const dbConfig = {
    user: config.database.user,
    password: config.database.password,
    database: config.database.name,
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0,
    enableKeepAlive: true,
    keepAliveInitialDelay: 0,
    connectTimeout: 10000,
    // Add retry strategy
    maxRetries: 3,
    retryDelay: 1000
  };

  // If using Unix Domain Socket
  if (process.env.DB_SOCKET_PATH) {
    dbConfig.socketPath = `${process.env.DB_SOCKET_PATH}`;
    console.log('Using Unix Domain Socket connection:', dbConfig.socketPath);
  } else {
    // If using TCP connection
    dbConfig.host = config.database.host;
    dbConfig.port = config.database.port || 3306;
    console.log('Using TCP connection:', dbConfig.host);
  }

  return mysql.createPool(dbConfig);
};

const pool = createPool();

// Add connection test with detailed error logging
const testConnection = async () => {
  try {
    const connection = await pool.getConnection();
    console.log('Database connected successfully');
    connection.release();
  } catch (err) {
    console.error('Database connection failed:', {
      code: err.code,
      errno: err.errno,
      sqlMessage: err.sqlMessage,
      sqlState: err.sqlState,
      host: config.database.host,
      user: config.database.user,
      database: config.database.name,
      socketPath: process.env.DB_SOCKET_PATH
    });
    
    // If the error is connection-related, attempt to reconnect
    if (err.code === 'ECONNREFUSED' || err.code === 'ER_ACCESS_DENIED_ERROR') {
      console.log('Attempting to reconnect in 5 seconds...');
      setTimeout(testConnection, 5000);
    }
  }
};

testConnection();

module.exports = pool;