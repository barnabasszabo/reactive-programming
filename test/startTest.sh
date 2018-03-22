#!/bin/bash

ulimit -n 65400

docker rm -f mongo node spring
docker run -d --name mongo -v `pwd`/.mongo:/data/db mongo
sleep 10s

CONCURENT=5000
TOTAL=500000
FILE=request_1

docker rm -f node spring
echo "Starting Spring server"
docker run -d --name spring -p 8080:8080 --link mongo:mongo loxon/meetup-reactive:spring5-1.0-alpine
sleep 15s

echo "Generate request"
wget "http://localhost:8080/api/generate?num=1&min=30&max=1000" -O ${FILE}.json
sleep 15s

echo "Start Webflux test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:8080/api/calculate > spring-webflux-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

echo "Start Parallel stream test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:8080/api/calculate/sync > spring-parallel-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

echo "Start Stream test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:8080/api/calculate/sync?type=STREAM > spring-stream-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

echo "Start Foreach test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:8080/api/calculate/sync?type=FOREACH > spring-foreach-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

echo "Starting node server"
docker rm -f node spring
docker run -d --name node -p 3000:3000 --link mongo:mongo loxon/meetup-reactive:node-1.0-alpine
sleep 15s

echo "Start Node test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:3000/calculate > node-reactive-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

# -----------------------------------

CONCURENT=200
TOTAL=10000
FILE=request_5000


docker rm -f node spring
echo "Starting Spring server"
docker run -d --name spring -p 8080:8080 --link mongo:mongo loxon/meetup-reactive:spring5-1.0-alpine
sleep 15s

echo "Generate request"
wget "http://localhost:8080/api/generate?num=5000&min=30&max=1000" -O ${FILE}.json
sleep 15s

echo "Start Webflux test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:8080/api/calculate > spring-webflux-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

echo "Start Parallel stream test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:8080/api/calculate/sync > spring-parallel-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

echo "Start Stream test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:8080/api/calculate/sync?type=STREAM > spring-stream-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

echo "Start Foreach test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:8080/api/calculate/sync?type=FOREACH > spring-foreach-${FILE}-c${CONCURENT}-n${TOTAL}.result
sleep 15s

echo "Starting node server"
docker rm -f node spring
docker run -d --name node -p 3000:3000 --link mongo:mongo loxon/meetup-reactive:node-1.0-alpine
sleep 15s

echo "Start Node test"
time ab -c $CONCURENT -n $TOTAL -p ${FILE}.json -T application/json -r -s 220 http://localhost:3000/calculate > node-reactive-${FILE}-c${CONCURENT}-n${TOTAL}.result
