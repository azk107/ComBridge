## ComBridge Backend

### Overview

This project involves a backend server deployed on Google Cloud Run, with the database hosted on Cloud SQL. The Auth Backend is built using Express.js to provide REST API endpoints for user data management. Additionally, ML Tflite model implemented in Flask with integrated for sign language 

## Tools

- **Node js & Express js**: Handles user authentication and data management.
- **Flask**: System integration between ML model and backend.
- **Cloud Run**: Serverless deployment for backend Authentication and ML model.
- **Cloud Storage**: Data storage for serving Image API.
- **Cloud SQL**: Manage database hosting.
- **NPM**: As package manager.

## Deployment

### Prerequisites
- Google Cloud account
- Dockerfile

### Step 1: Setup Google Cloud Project
1. **Create a new project** in the Google Cloud Console.
2. **Enable the necessary APIs**:
    - Cloud Run
    - Cloud SQL
    - Artifact Registry
3. **Clone your repo in google cloud shell**
4. **Create a Cloud SQL instance**:
    - Go to the SQL section in the Google Cloud Console.
    - Create a new MySql instance.
    - Note down the connection name, database name, username, and password.
5. **Create an Artifact Registry repository**:
    - Go to the Artifact Registry section in the Google Cloud Console.
    - Create a new Docker repository.
    - Note down the repository name and location.
