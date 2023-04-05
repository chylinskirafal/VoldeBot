FROM openjdk:17
MAINTAINER chylu.pl
COPY target/VoldeBot-1.0-SNAPSHOT.jar app.jar
COPY .env .env
ENTRYPOINT ["java","-jar","/app.jar"]