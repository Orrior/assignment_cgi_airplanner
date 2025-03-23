#build spring application
FROM maven:3.9.9-amazoncorretto-21-alpine
WORKDIR /app
COPY . .
RUN mvn clean package
#run spring application
FROM amazoncorretto:21
WORKDIR /app
EXPOSE 8080
COPY target/cgi_assignment-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
