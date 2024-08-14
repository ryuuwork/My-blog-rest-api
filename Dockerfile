FROM sapmachine:17
WORKDIR /app
COPY /target/my-blog-rest-api-0.0.1-SNAPSHOT.jar /app/my-blog-rest-api.jar
ENTRYPOINT ["java","-jar","my-blog-rest-api.jar"]