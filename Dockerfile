FROM openjdk:17-oracle
WORKDIR /app
COPY target/deal.jar /app/deal.jar
EXPOSE 8000
CMD ["java", "-jar", "deal.jar"]