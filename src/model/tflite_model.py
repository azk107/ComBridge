"""TFLite model wrapper for sign language detection."""
import numpy as np
import tflite_runtime.interpreter as tflite
from typing import Dict, Any
from .preprocessing import preprocess_image, decode_base64_image
from .labels import get_label_from_index

class SignLanguageModel:
    def __init__(self, model_path: str):
        """Initialize TFLite model for sign language detection."""
        self.interpreter = self._load_model(model_path)
        self.input_details, self.output_details = self._get_model_details()
        
    def _load_model(self, model_path: str) -> tflite.Interpreter:
        """Load and initialize TFLite model."""
        try:
            interpreter = tflite.Interpreter(model_path=model_path)
            interpreter.allocate_tensors()
            return interpreter
        except Exception as e:
            raise RuntimeError(f"Failed to load model: {str(e)}")
            
    def _get_model_details(self):
        """Get model input and output details."""
        return (
            self.interpreter.get_input_details(),
            self.interpreter.get_output_details()
        )
        
    def predict(self, base64_image: str) -> Dict[str, Any]:
        """Predict sign language from base64 image.
        
        Args:
            base64_image: Base64 encoded image string
            
        Returns:
            Dictionary containing prediction results
        """
        # Decode and preprocess image
        image_data = decode_base64_image(base64_image)
        processed_input = preprocess_image(image_data)
        
        # Set input tensor
        self.interpreter.set_tensor(
            self.input_details[0]['index'],
            processed_input
        )
        
        # Run inference
        self.interpreter.invoke()
        
        # Get predictions
        predictions = self.interpreter.get_tensor(
            self.output_details[0]['index']
        )
        
        # Get top prediction
        pred_index = np.argmax(predictions[0])
        confidence = float(predictions[0][pred_index])
        
        return {
            'label': get_label_from_index(pred_index),
            'confidence': confidence,
            'predictions': predictions[0].tolist()
        }