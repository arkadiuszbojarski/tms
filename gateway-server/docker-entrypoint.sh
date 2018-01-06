#!/bin/sh
while ! echo 'GET /eureka/apps/config HTTP/1.0' | nc -z discovery-server 8761; do
    echo "Awaiting for config server registration..."
    sleep 10
done
java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=container -jar /target/app.jar