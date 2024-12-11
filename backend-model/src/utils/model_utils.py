"""Utility functions for model operations."""
import numpy as np
from typing import List, Tuple, Any

def validate_input(input_data: List[float], expected_shape: Tuple[int, ...]) -> bool:
    """Validate input data against expected shape."""
    try:
        total_elements = np.prod(expected_shape)
        return len(input_data) == total_elements
    except Exception:
        return False

def reshape_input(input_data: List[float], target_shape: Tuple[int, ...]) -> np.ndarray:
    """Reshape input data to match model requirements."""
    return np.array(input_data, dtype=np.float32).reshape(target_shape)

def format_prediction(
    predictions: np.ndarray,
    output_shape: Tuple[int, ...]
) -> Tuple[List[Any], List[int]]:
    """Format model predictions for API response."""
    return predictions.tolist(), list(output_shape)