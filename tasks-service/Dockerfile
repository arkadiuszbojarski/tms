FROM openjdk:8-jdk-alpine

ARG JAR_FILE
ADD ${JAR_FILE} target/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","target/app.jar"]
COPY files/UnlimitedJCEPolicyJDK8/* \ /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/