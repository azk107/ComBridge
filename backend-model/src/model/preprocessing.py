# """Image preprocessing utilities for sign language detection."""
# import numpy as np
# from PIL import Image
# import io
# import base64

# def preprocess_image(image_data: bytes, target_size=(224, 224)):
#     """Preprocess image for model input.
    
#     Args:
#         image_data: Raw image bytes
#         target_size: Target size for model input
        
#     Returns:
#         Preprocessed image array
#     """
#     # Convert bytes to PIL Image
#     image = Image.open(io.BytesIO(image_data))
    
#     # Resize image
#     image = image.resize(target_size)
    
#     # Convert to array and preprocess
#     img_array = np.array(image)
#     img_array = img_array.astype('float32')
#     img_array = img_array / 255.0
#     img_array = np.expand_dims(img_array, axis=0)
    
#     return img_array

# def decode_base64_image(base64_string: str) -> bytes:
#     """Decode base64 image to bytes."""
#     try:
#         # Remove data URL prefix if present
#         if 'base64,' in base64_string:
#             base64_string = base64_string.split('base64,')[1]
#         return base64.b64decode(base64_string)
#     except Exception as e:
#         raise ValueError(f"Invalid base64 image: {str(e)}")

"""Image preprocessing utilities for sign language detection."""
import numpy as np
from PIL import Image
import io

def preprocess_image(image_data: bytes, target_size=(224, 224)):
    """Preprocess image for model input.
    
    Args:
        image_data: Raw image bytes
        target_size: Target size for model input
        
    Returns:
        Preprocessed image array
    """
    # Convert bytes to PIL Image
    image = Image.open(io.BytesIO(image_data))
    
    # Resize image
    image = image.resize(target_size)
    
    # Convert to array and preprocess
    img_array = np.array(image)
    img_array = img_array.astype('float32')
    img_array = img_array / 255.0
    img_array = np.expand_dims(img_array, axis=0)
    
    return img_array