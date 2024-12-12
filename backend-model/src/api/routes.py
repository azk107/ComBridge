# """API routes for sign language detection."""
# from flask import Blueprint, jsonify, request
# from src.model.tflite_model import SignLanguageModel
# from src.config import MODEL_PATH
# from src.model.labels import LABELS

# # Create blueprint
# api = Blueprint('api', __name__)

# # Initialize model
# model = SignLanguageModel(MODEL_PATH)

# @api.route('/detect', methods=['POST'])
# def detect_sign():
#     """Endpoint for sign language detection."""
#     try:
#         data = request.get_json()
        
#         if not data or 'image' not in data:
#             return jsonify({
#                 'error': 'Invalid input. Expected {"image": "base64_image_string"}'
#             }), 400
            
#         result = model.predict(data['image'])
        
#         return jsonify(result)
        
#     except Exception as e:
#         return jsonify({'error': str(e)}), 500

# @api.route('/labels', methods=['GET'])
# def get_labels():
#     """Get available sign language labels."""
#     return jsonify({
#         'labels': LABELS
#     })

# @api.route('/health', methods=['GET'])
# def health():
#     """Health check endpoint."""
#     return jsonify({'status': 'healthy'})

# # Add the old predict endpoint for backward compatibility
# @api.route('/predict', methods=['POST'])
# def predict():
#     """Legacy endpoint for model predictions."""
#     try:
#         data = request.get_json()
#         if not data or 'input' not in data:
#             return jsonify({
#                 'error': 'Invalid input. Expected {"input": [...]}' 
#             }), 400
            
#         input_data = data['input']
#         result = model.predict_raw(input_data)
        
#         return jsonify(result)
        
#     except Exception as e:
#         return jsonify({'error': str(e)}), 500

"""API routes for sign language detection."""
from flask import Blueprint, jsonify, request
from werkzeug.utils import secure_filename
import os
from src.model.tflite_model import SignLanguageModel
from src.config import MODEL_PATH, UPLOAD_FOLDER
from src.model.labels import LABELS

# Create blueprint
api = Blueprint('api', __name__)

# Initialize model
model = SignLanguageModel(MODEL_PATH)

# Ensure upload folder exists
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

def allowed_file(filename):
    """Check if file extension is allowed."""
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in {'png', 'jpg', 'jpeg', 'gif'}

@api.route('/detect', methods=['POST'])
def detect_sign():
    """Endpoint for sign language detection using image file."""
    try:
        # Check if image file is present in request
        if 'image' not in request.files:
            return jsonify({
                'error': 'No image file provided'
            }), 400
            
        file = request.files['image']
        
        # Check if file is selected
        if file.filename == '':
            return jsonify({
                'error': 'No selected file'
            }), 400
            
        # Check file type
        if not allowed_file(file.filename):
            return jsonify({
                'error': 'Invalid file type. Allowed types: png, jpg, jpeg, gif'
            }), 400
            
        # Read file contents
        image_data = file.read()
        
        # Make prediction
        result = model.predict(image_data)
        
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