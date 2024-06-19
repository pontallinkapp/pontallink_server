FROM openjdk:17
ADD ./docker-spring-boot.jar docker-spring-boot.jar
LABEL authors="eumesmo"

ENTRYPOINT ["java", "-jar", "docker-spring-boot.jar"]