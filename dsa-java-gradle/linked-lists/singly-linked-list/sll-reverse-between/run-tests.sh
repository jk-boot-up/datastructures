#!/bin/bash

docker compose build


# Run tests
docker-compose --profile test up
