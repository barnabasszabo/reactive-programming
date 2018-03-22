#!/bin/bash

docker image rm loxon/meetup-reactive:node-1.1-alpine
docker build --pull -t loxon/meetup-reactive:node-1.1-alpine .
# docker push loxon/meetup-reactive:node-1.0-alpine
