FROM openjdk:8
RUN mkdir doldolseo
WORKDIR ./doldolseo
RUN apt-get update
RUN apt-get install -y vim net-tools nmap iputils-ping
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar
ENV JAR_FILE_PATH=./build/libs
WORKDIR $JAR_FILE_PATH
RUN pwd > temp.txt
COPY $JAR_FILE_PATH/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]