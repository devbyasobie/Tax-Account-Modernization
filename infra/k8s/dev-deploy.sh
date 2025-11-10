#!/usr/bin/env bash
set -e

SERVICE_DIR=services/account-service
IMAGE_NAME=account-service:local
NAMESPACE=tax-modernization

echo "🔨 Building Maven project..."
cd "$SERVICE_DIR"
mvn clean package -DskipTests

echo "🐳 Building Docker image..."
docker build -t "$IMAGE_NAME" .

echo "📦 Loading image into minikube..."
minikube image load "$IMAGE_NAME"

echo "🚀 Applying Kubernetes manifests..."
cd ../../
kubectl apply -f infra/k8s/account-deployment.yaml
kubectl apply -f infra/k8s/account-service.yaml

echo "⏳ Waiting for rollout..."
kubectl rollout status deploy/account-service -n "$NAMESPACE"

echo "✅ Done."
