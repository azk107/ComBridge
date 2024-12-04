const logger = {
  info: (message, meta = {}) => {
    console.log(JSON.stringify({ severity: 'INFO', message, ...meta }));
  },
  error: (message, error, meta = {}) => {
    console.error(JSON.stringify({
      severity: 'ERROR',
      message,
      error: error.message,
      stack: error.stack,
      ...meta
    }));
  }
};

module.exports = logger;