FROM maven:3.8.1-openjdk-11-slim as build
MAINTAINER Oliven C. Barcelon <olivenbarcelon@gmail.com>

WORKDIR /app

COPY ./project/mvnw .
COPY ./project/.mvn .mvn

COPY ./project/pom.xml .

RUN mvn dependency:go-offline -B

COPY ./project/src src

RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11.0.11-slim
ARG DEPENDENCY=/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8082
ENTRYPOINT ["java","-cp","app:app/lib/*","io.github.olivenbarcelon.umjavaws.UmJavaWsApplication"]