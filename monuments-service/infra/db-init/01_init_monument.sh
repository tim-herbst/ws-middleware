#!/bin/bash

psql -v ON_ERROR_STOP=1 --dbname "middleware" --username "postgres" <<-EOSQL
  CREATE SCHEMA monument;
  COMMIT;

  CREATE USER monument WITH PASSWORD '123456';

  GRANT CONNECT ON DATABASE "middleware" TO monument;

  GRANT USAGE ON SCHEMA "monument" TO monument;
  GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA "monument" TO monument;
  GRANT ALL PRIVILEGES ON SCHEMA "monument" TO monument;
  ALTER SCHEMA "monument" OWNER TO monument;
  COMMIT;
EOSQL
