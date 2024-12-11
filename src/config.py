import os

# Model Configuration
MODEL_PATH = os.getenv('MODEL_PATH', 'model.tflite')

# Server Configuration
PORT = int(os.getenv('PORT', 8080))
HOST = os.getenv('HOST', '0.0.0.0')

# API Configuration
API_PREFIX = '/api/v1'