FROM openjdk:8-jdk-alpine

RUN apk --no-cache add netcat-openbsd
ARG JAR_FILE
ADD ${JAR_FILE} target/app.jar
ADD docker-entrypoint.sh docker-entrypoint.sh
RUN chmod 755 docker-entrypoint.sh
COPY files/UnlimitedJCEPolicyJDK8/* /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
ENTRYPOINT ["/docker-entrypoint.sh"]