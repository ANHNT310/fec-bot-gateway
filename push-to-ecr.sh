#!/bin/bash

REGION="ap-southeast-1"
ACCOUNT_ID="047719629347"
REPO_NAME="fec-bot/gateway-service"
IMAGE_NAME="fec-bot/gateway-service"
IMAGE_TAG="latest"

echo "==> Logging in to AWS ECR..."
aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin ${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com

echo "==> Building Docker image..."
docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .

echo "==> Tagging image..."
docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${REPO_NAME}:${IMAGE_TAG}

echo "==> Pushing image to ECR..."
docker push ${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${REPO_NAME}:${IMAGE_TAG}

echo "✅ Done. Image pushed to ECR successfully."
