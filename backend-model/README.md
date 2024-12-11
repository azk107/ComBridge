# TFLite Model API Service

This service provides a REST API for making predictions using a TFLite model.

## Local Development

1. Install dependencies:
```bash
pip install -r requirements.txt
```

2. Place your TFLite model in the root directory as `model.tflite`

3. Run the application:
```bash
python -m flask run
```

## Docker Build and Run

1. Build the Docker image:
```bash
docker build -t tflite-api .
```

2. Run the container:
```bash
docker run -p 8080:8080 -v $(pwd)/model.tflite:/app/model.tflite tflite-api
```

## Deploy to Cloud Run

1. Build and push to Google Container Registry:
```bash
gcloud builds submit --tag gcr.io/[PROJECT_ID]/tflite-api
```

2. Deploy to Cloud Run:
```bash
gcloud run deploy tflite-api \
  --image gcr.io/[PROJECT_ID]/tflite-api \
  --platform managed \
  --allow-unauthenticated
```

## API Endpoints

### POST /predict
Make predictions using the model.

Request body:
```json
{
    "input": [1.0, 2.0, 3.0, ...]
}
```

Response:
```json
{
    "prediction": [[0.1, 0.2, ...]],
    "shape": [1, 10]
}
```

### GET /health
Check service health.

Response:
```json
{
    "status": "healthy"
}
```