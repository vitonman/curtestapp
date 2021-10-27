FROM openjdk:11
VOLUME /main-app
ADD target/docker-currency-navigator.jar docker-currency-navigator.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-currency-navigator.jar"]