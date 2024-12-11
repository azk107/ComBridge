import numpy as np
import tflite_runtime.interpreter as tflite
from typing import Tuple, List, Any

class TFLiteModel:
    def __init__(self, model_path: str):
        self.interpreter = self._load_model(model_path)
        self.input_details, self.output_details = self._get_model_details()

    def _load_model(self, model_path: str) -> tflite.Interpreter:
        """Load and initialize TFLite model."""
        interpreter = tflite.Interpreter(model_path=model_path)
        interpreter.allocate_tensors()
        return interpreter

    def _get_model_details(self) -> Tuple[List[dict], List[dict]]:
        """Get model input and output details."""
        return (
            self.interpreter.get_input_details(),
            self.interpreter.get_output_details()
        )

    def preprocess_input(self, input_data: List[float]) -> np.ndarray:
        """Preprocess input data for model."""
        input_array = np.array(input_data, dtype=np.float32)
        input_shape = self.input_details[0]['shape']
        return np.reshape(input_array, input_shape)

    def predict(self, input_data: List[float]) -> Tuple[List[Any], List[int]]:
        """Make prediction using the model."""
        processed_input = self.preprocess_input(input_data)
        
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
        
        return output_data.tolist(), output_data.shape