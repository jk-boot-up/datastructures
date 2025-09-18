#!/bin/bash
set -euo pipefail

# Image name/tag
IMAGE_NAME="sll-reverse-between"

# Make a timestamp (e.g. 2025-09-18_18-45-12)
TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")

# Log file with timestamp
LOG_FILE="./logs/docker-build.log"

echo ">>> Starting Docker build for $IMAGE_NAME"
echo ">>> Logs will be saved to $LOG_FILE"

# Run docker build, send logs to both console and file
docker build --no-cache --progress=plain -t "$IMAGE_NAME" . 2>&1 | tee "$LOG_FILE"

echo ">>> Build finished. Logs stored in $LOG_FILE"
