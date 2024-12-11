from flask import Blueprint, jsonify, request
from src.model.tflite_model import TFLiteModel
from src.config import MODEL_PATH

# Create blueprint
api = Blueprint('api', __name__)

# Initialize model
model = TFLiteModel(MODEL_PATH)

@api.route('/predict', methods=['POST'])
def predict():
    """Endpoint for model predictions."""
    try:
        data = request.get_json()
        
        if not data or 'input' not in data:
            return jsonify({
                'error': 'Invalid input data. Expected {"input": [...]}'}
            ), 400
            
        predictions, shape = model.predict(data['input'])
        
        return jsonify({
            'prediction': predictions,
            'shape': shape
        })
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@api.route('/health', methods=['GET'])
def health():
    """Health check endpoint."""
    return jsonify({'status': 'healthy'})