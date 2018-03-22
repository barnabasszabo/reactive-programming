# Reactive programming
Why(?) is this good for us?

This repository contains the source files about Reactive programming meetup.
We talked about what is the reactive programming, why to use it, and how can we use it in Node, and Java world.

## Presentation
https://docs.google.com/presentation/d/1GsbnSRerh7ICy8e9MqzhDS2IQIYumDK452zUKBAVRgQ/edit?usp=sharing

## Folder Structure
* **reactivity-node**: Node implementation of the reactive programming
* **reactivity-spring**: Spring implementation of the reactive programming, and non reactive programming
* **test**: Performance tests

# Application
Our application (in every implementation) is a simple calculation application.
The calculation instructions comes from the joson, and send it in HTTP REST protocol.

# Sample request
```json
[
    {
        "operation": "MULTIPLY",
        "leftValue": 1,
        "rightValue": 2,
        "leftOperation": null,
        "rightOperation": null
    }
]
```
Available operations:  ``` ADD MULTIPLY, READFILE, READDB, PI ```

### How to generate request json
Start spring application and call:
``` sh
ELEMENT_NUM=5000 && wget "http://localhost:8080/api/generate?num=${ELEMENT_NUM}&min=30&max=1000" -O request.json
```

## How to run Node application
#### Prerequisite
* Docker-ce
#### Build & Run
```sh
cd reactivity-node 
./1-create-new-image.sh
./2-start-ecosystem.sh
```
#### URL
```sh
curl -X POST http://localhost:3000/calculate -d @request.json --header "Content-Type: application/json"
```

## How to run Spring application
#### Prerequisite
* Docker-ce
* JDK8
#### Build & Run
```sh
cd reactivity-spring
./1-create-new-image.sh
./2-start-ecosystem.sh
```
#### URL
```sh

# Webflux
curl -X POST http://localhost:8080/api/calculate -d @request.json --header "Content-Type: application/json" 

# Parralel stream
curl -X POST http://localhost:8080/api/calculate/sync -d @request.json --header "Content-Type: application/json" 

# Stream
curl -X POST http://localhost:8080/api/calculate/sync?type=STREAM -d @request.json --header "Content-Type: application/json" 

# Foreach
curl -X POST http://localhost:8080/api/calculate/sync?type=FOREACH -d @request.json --header "Content-Type: application/json" 
```
