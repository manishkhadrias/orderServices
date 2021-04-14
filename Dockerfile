FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
EXPOSE 8000
ADD target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]