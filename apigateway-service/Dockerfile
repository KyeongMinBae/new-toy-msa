FROM openjdk:21-ea-jdk-slim
VOLUME /tmp
COPY target/apigateway-service-1.0.jar ApigatewayService.jar
ENTRYPOINT ["java", "-jar", "ApigatewayService.jar"]