#!/bin/bash

psql -v ON_ERROR_STOP=1 --dbname "postgres" --username "postgres" <<-EOSQL
    CREATE DATABASE "middleware";
EOSQL
