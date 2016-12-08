FROM java:8-jre
COPY service/build/libs /opt/app/
WORKDIR /opt/app
CMD ["java", "-jar", "hello-world-java-0.1.0.jar"]