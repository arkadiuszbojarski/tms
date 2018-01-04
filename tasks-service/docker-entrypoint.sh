#!/bin/sh
while ! nc -z ${DATABASE_HOST} ${DATABASE_PORT}; do
    echo "Trying to connect to MySQL tasks database at ${DATABASE_HOST}:${DATABASE_PORT}..."
    sleep 10
done
java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=container -jar /target/app.jar