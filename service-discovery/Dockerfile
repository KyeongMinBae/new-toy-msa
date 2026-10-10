FROM openjdk:21-ea-jdk-slim
VOLUME /tmp
COPY target/service-discovery-1.0.jar DiscoveryService.jar
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]