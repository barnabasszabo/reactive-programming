#!/bin/bash

docker rm -f mongo node

docker run -d --name mongo -v ~/workspace/meetup/reactive_meetup/reactivity-node/.mongo:/data/db mongo
sleep 5s
docker run -d --name node -p 3000:3000 --link mongo:mongo loxon/meetup-reactive:node-1.0-alpine
