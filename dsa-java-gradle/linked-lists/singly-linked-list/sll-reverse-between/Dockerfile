FROM gradle:9.0.0-jdk24 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean build --no-daemon --stacktrace --info


FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=build /home/gradle/project/app/build/libs/sll-reverse-between.jar sll-reverse-between.jar
CMD ["java", "-jar", "sll-reverse-between.jar"]
