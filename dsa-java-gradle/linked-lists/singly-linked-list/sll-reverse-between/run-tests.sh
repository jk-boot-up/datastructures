#!/bin/bash

# Run tests using Docker Compose
docker compose --profile test up --abort-on-container-exit --exit-code-from test

# Show test logs if available
if [ -f ./logs/build.log ]; then
  echo "Build log:"
  cat ./logs/build.log
fi

if [ -f ./logs/test.log ]; then
  echo "Test log:"
  cat ./logs/test.log
fi
