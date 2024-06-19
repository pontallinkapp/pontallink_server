FROM openjdk:17
ADD ./docker-spring-boot.war docker-spring-boot.war
LABEL authors="eumesmo"

ENTRYPOINT ["java", "-war", "docker-spring-boot.war"]