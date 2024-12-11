"""API routes for sign language detection."""
from flask import Blueprint, jsonify, request
from src.model.tflite_model import SignLanguageModel
from src.config import MODEL_PATH
from src.model.labels import LABELS

# Create blueprint
api = Blueprint('api', __name__)

# Initialize model
model = SignLanguageModel(MODEL_PATH)

@api.route('/detect', methods=['POST'])
def detect_sign():
    """Endpoint for sign language detection."""
    try:
        data = request.get_json()
        
        if not data or 'image' not in data:
            return jsonify({
                'error': 'Invalid input. Expected {"image": "base64_image_string"}'
            }), 400
            
        result = model.predict(data['image'])
        
        return jsonify(result)
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@api.route('/labels', methods=['GET'])
def get_labels():
    """Get available sign language labels."""
    return jsonify({
        'labels': LABELS
    })

@api.route('/health', methods=['GET'])
def health():
    """Health check endpoint."""
    return jsonify({'status': 'healthy'})