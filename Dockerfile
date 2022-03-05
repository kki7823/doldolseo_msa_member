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
RUN ./gradlew --stacktrace bootJar
ENV JAR_FILE_PATH=./build/libs
RUN pwd > temp.txt
#COPY $JAR_FILE_PATH/*.jar $JAR_FILE_PATH/app.jar
RUN cp $JAR_FILE_PATH/doldolseo_msa_member-0.0.1-SNAPSHOT.jar $JAR_FILE_PATH/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/app.jar"]