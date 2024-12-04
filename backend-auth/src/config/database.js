const mysql = require('mysql2');
require('dotenv').config();

const createPool = () => {
  const config = {
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
  };

  // Check if running in production (Cloud Run)
  if (process.env.NODE_ENV === 'production') {
    return mysql.createPool({
      ...config,
      socketPath: `/cloudsql/${process.env.INSTANCE_CONNECTION_NAME}`,
    });
  }

  // Local development configuration
  return mysql.createPool({
    ...config,
    host: process.env.DB_HOST,
  });
};

const pool = createPool();

module.exports = pool.promise();