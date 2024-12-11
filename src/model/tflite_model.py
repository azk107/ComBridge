"""TFLite model wrapper for inference."""
import numpy as np
import tflite_runtime.interpreter as tflite
from typing import Tuple, List, Any
from src.utils import validate_input, reshape_input, format_prediction

class TFLiteModel:
    def __init__(self, model_path: str):
        """Initialize TFLite model.
        
        Args:
            model_path: Path to the TFLite model file
        """
        self.interpreter = self._load_model(model_path)
        self.input_details, self.output_details = self._get_model_details()
        self.input_shape = self.input_details[0]['shape']

    def _load_model(self, model_path: str) -> tflite.Interpreter:
        """Load and initialize TFLite model."""
        try:
            interpreter = tflite.Interpreter(model_path=model_path)
            interpreter.allocate_tensors()
            return interpreter
        except Exception as e:
            raise RuntimeError(f"Failed to load model: {str(e)}")

    def _get_model_details(self) -> Tuple[List[dict], List[dict]]:
        """Get model input and output details."""
        return (
            self.interpreter.get_input_details(),
            self.interpreter.get_output_details()
        )

    def predict(self, input_data: List[float]) -> Tuple[List[Any], List[int]]:
        """Make prediction using the model.
        
        Args:
            input_data: List of input values
            
        Returns:
            Tuple of (predictions, shape)
            
        Raises:
            ValueError: If input data is invalid
        """
        if not validate_input(input_data, self.input_shape):
            raise ValueError(
                f"Invalid input shape. Expected {self.input_shape}"
            )

        processed_input = reshape_input(input_data, self.input_shape)
        
        # Set input tensor
        self.interpreter.set_tensor(
            self.input_details[0]['index'], 
            processed_input
        )
        
        # Run inference
        self.interpreter.invoke()
        
        # Get output tensor
        output_data = self.interpreter.get_tensor(
            self.output_details[0]['index']
        )
        
        return format_prediction(output_data, output_data.shape)