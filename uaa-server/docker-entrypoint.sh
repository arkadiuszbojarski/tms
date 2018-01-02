#!/bin/sh
while ! nc -z configuration-server 8888 ; do
    echo "Waiting for upcoming Config Server"
    sleep 10
done
java -jar /target/app.jar