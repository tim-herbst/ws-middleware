#!/bin/bash

function check_system() {
  if [[ $(which mvn | wc -l) -ne 1 ]]; then
    echo "maven not installed, please install:"
    echo "https://maven.apache.org/install.html"
    exit 1
  fi
  if [[ $(which java | wc -l) -ne 1 ]]; then
      echo "java (version=11) is not installed, please install:"
      echo "https://adoptium.net/de/installation/"
      exit 1
  fi
  if [[ $(docker --version | grep -c "Docker version") -ne 1 ]]; then
    echo "docker not installed, please install:"
    echo "https://docs.docker.com/get-docker/"
    exit 1
  fi
  if [[ $(which node | wc -l) -ne 1 ]]; then
    echo "node is not installed, please install:"
    echo "(version used for development v18.12.1)"
    echo "https://nodejs.org/en/download/"
    exit 1
  fi
  echo "system requirements fulfilled, proceeding with building images."
}

function build_backend() {
  cd ../ || (echo "trouble while switching directories" && exit 1)
  mvn clean verify
  mvn docker:build
}

function build_frontend() {
  cd ../ui/ || (echo "trouble while switching to ui-directory" && exit 1)
  docker build -t timherbst/monuments-ui:0.0.1 .
}

function start_local_system() {
  cd ../monuments-service/infra/ || (echo "trouble while switching directories" && exit 1)
  docker-compose -f ./infra-services.yml up -d
}

function deploy_to_docker_hub() {
  echo "deploying ui and backend to docker-hub acount=(timherbst)"
  docker push timherbst/monuments-ui:0.0.1
  docker push timherbst/monuments-service:0.0.1
}

check_system
build_backend
build_frontend
start_local_system

if [[ -z "$1" ]]; then
  echo "No deployment argument passed."
else
  deploy_to_docker_hub
fi
