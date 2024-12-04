#!/bin/bash

# Create secrets in Secret Manager
echo "Creating secrets in Secret Manager..."
echo "your-db-password" | gcloud secrets create auth-db-password --data-file=-
echo "your-jwt-secret" | gcloud secrets create jwt-secret --data-file=-

# Create Cloud SQL instance
echo "Creating Cloud SQL instance..."
gcloud sql instances create auth-db-instance \
    --database-version=MYSQL_8_0 \
    --tier=db-f1-micro \
    --region=${_REGION} \
    --root-password=your-secure-password

# Create database
echo "Creating database..."
gcloud sql databases create sign_language_auth \
    --instance=auth-db-instance

# Import schema
echo "Importing schema..."
gcloud sql import sql auth-db-instance \
    gs://${PROJECT_ID}-sql-scripts/database.sql \
    --database=sign_language_auth