# 基於官方的Java鏡像
FROM openjdk:21-jdk
VOLUME /tmp
COPY target/spring-boot-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
