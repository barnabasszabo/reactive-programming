#!/bin/bash

docker rm -f mongo
docker run -d --name mongo -v `pwd`/.mongo:/data/db mongo


docker rm -f node spring
echo "Starting Spring server"
docker run -d --name spring -p 8080:8080 --link mongo:mongo loxon/meetup-reactive:spring5-1.0-alpine
sleep 30s

echo "Generate large request"
wget "http://localhost:8080/api/generate?num=1000000&min=30&max=1000" -O request_1000000.json
sleep 10s

echo "Start Webflux test"
time curl -X POST http://localhost:8080/api/calculate -d @request_1000000.json --header "Content-Type: application/json" --output /dev/null
sleep 5s
docker rm -f node spring
echo "Starting Spring server"
docker run -d --name spring -p 8080:8080 --link mongo:mongo loxon/meetup-reactive:spring5-1.0-alpine
sleep 30s

echo "Start Parallel stream test"
time curl -X POST http://localhost:8080/api/calculate/sync -d @request_1000000.json --header "Content-Type: application/json" --output /dev/null
sleep 5s
docker rm -f node spring
echo "Starting Spring server"
docker run -d --name spring -p 8080:8080 --link mongo:mongo loxon/meetup-reactive:spring5-1.0-alpine
sleep 30s

echo "Start Stream test"
time curl -X POST http://localhost:8080/api/calculate/sync?type=STREAM -d @request_1000000.json --header "Content-Type: application/json" --output /dev/null
sleep 5s
docker rm -f node spring
echo "Starting Spring server"
docker run -d --name spring -p 8080:8080 --link mongo:mongo loxon/meetup-reactive:spring5-1.0-alpine
sleep 30s

echo "Start Foreach test"
time curl -X POST http://localhost:8080/api/calculate/sync?type=FOREACH -d @request_1000000.json --header "Content-Type: application/json" --output /dev/null
sleep 5s
docker rm -f node spring

echo "Starting node server"
docker rm -f node spring
docker run -d --name node -p 3000:3000 --link mongo:mongo loxon/meetup-reactive:node-1.0-alpine
sleep 10s

echo "Start Node test"
time curl -X POST http://localhost:3000/calculate -d @request_1000000.json --header "Content-Type: application/json" --output /dev/null
