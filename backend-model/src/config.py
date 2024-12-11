# import os

# # Model Configuration
# MODEL_DIR = os.path.join(os.path.dirname(os.path.dirname(__file__)), 'models')
# MODEL_PATH = os.getenv('MODEL_PATH', os.path.join(MODEL_DIR, 'model.tflite'))

# # Server Configuration
# PORT = int(os.getenv('PORT', 8080))
# HOST = os.getenv('HOST', '0.0.0.0')

# # API Configuration
# API_PREFIX = '/api/v1'

"""Application configuration."""
import os

# Model Configuration
MODEL_DIR = os.path.join(os.path.dirname(os.path.dirname(__file__)), 'models')
MODEL_PATH = os.getenv('MODEL_PATH', os.path.join(MODEL_DIR, 'model.tflite'))

# Server Configuration
PORT = int(os.getenv('PORT', 8080))
HOST = os.getenv('HOST', '0.0.0.0')

# API Configuration
API_PREFIX = '/api/v1'

# Upload Configuration
UPLOAD_FOLDER = os.path.join(os.path.dirname(os.path.dirname(__file__)), 'uploads')
MAX_CONTENT_LENGTH = 16 * 1024 * 1024  # 16MB max file size