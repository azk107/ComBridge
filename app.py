# from flask import Flask, request, jsonify
# import numpy as np
# import tflite_runtime.interpreter as tflite
# import os

# app = Flask(__name__)

# # Load TFLite model
# def load_model(model_path):
#     interpreter = tflite.Interpreter(model_path=model_path)
#     interpreter.allocate_tensors()
#     return interpreter

# # Get model details
# def get_model_details(interpreter):
#     input_details = interpreter.get_input_details()
#     output_details = interpreter.get_output_details()
#     return input_details, output_details

# # Initialize model
# MODEL_PATH = os.getenv('MODEL_PATH', 'model.tflite')
# interpreter = load_model(MODEL_PATH)
# input_details, output_details = get_model_details(interpreter)

# @app.route('/predict', methods=['POST'])
# def predict():
#     try:
#         # Get input data from request
#         data = request.get_json()
#         input_data = np.array(data['input'], dtype=np.float32)
        
#         # Reshape input data according to model's input shape
#         input_shape = input_details[0]['shape']
#         input_data = np.reshape(input_data, input_shape)
        
#         # Set input tensor
#         interpreter.set_tensor(input_details[0]['index'], input_data)
        
#         # Run inference
#         interpreter.invoke()
        
#         # Get output tensor
#         output_data = interpreter.get_tensor(output_details[0]['index'])
        
#         return jsonify({
#             'prediction': output_data.tolist(),
#             'shape': output_data.shape
#         })
    
#     except Exception as e:
#         return jsonify({'error': str(e)}), 500

# @app.route('/health', methods=['GET'])
# def health():
#     return jsonify({'status': 'healthy'})

"""Main application entry point."""
from src.api import create_app

app = create_app()

if __name__ == '__main__':
    from src.config import PORT, HOST
    app.run(host=HOST, port=PORT)