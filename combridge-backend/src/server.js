const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
require('dotenv').config();

const { getConfig, validateEnv } = require('./config/env');
const authRoutes = require('./routes/auth');

// Validate environment variables before starting the server
try {
  validateEnv();
} catch (error) {
  console.error('Environment validation failed:', error.message);
  process.exit(1);
}

const config = getConfig();
const app = express();

// Middleware
app.use(helmet());
app.use(cors(config.cors));
app.use(express.json());

// Routes
app.use('/api/auth', authRoutes);

// Health check endpoint
app.get('/health', (req, res) => {
  res.status(200).json({ status: 'healthy' });
});

app.listen(config.port, () => {
  console.log(`Server is running on port ${config.port}`);
});