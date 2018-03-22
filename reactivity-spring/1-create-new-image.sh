#!/bin/bash

./mvnw clean package
docker build -t loxon/meetup-reactive:spring5-1.0-alpine .
# docker push loxon/meetup-reactive:spring5-1.0-alpine
