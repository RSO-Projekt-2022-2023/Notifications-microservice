FROM amazoncorretto:18
RUN mkdir /app

WORKDIR /app

ADD ./api/target/notifications-api-1.0.0-SNAPSHOT.jar /app
ENV KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-image-metadata:5432/image-metadata KUMULUZEE_DATASOURCES0_USERNAME=dbuser KUMULUZEE_DATASOURCES0_PASSWORD=postgres
EXPOSE 8080

CMD ["java", "-jar", "notifications-api-1.0.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "image-catalog-api-1.0.0-SNAPSHOT.jar"]
#CMD java -jar image-catalog-api-1.0.0-SNAPSHOT.jar
