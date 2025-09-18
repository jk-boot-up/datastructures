FROM gradle:9.0.0-jdk24 AS build
WORKDIR /home/gradle/project
COPY . .

# compute the JDK root from javac and pass it to Gradle command
RUN REAL_JAVA_HOME=$(dirname $(dirname $(readlink -f $(which javac)))) \
  && echo "REAL_JAVA_HOME=$REAL_JAVA_HOME" \
  && $REAL_JAVA_HOME/bin/java -version \
  && $REAL_JAVA_HOME/bin/javac -version \
  && gradle clean build --no-daemon --stacktrace --info -Dorg.gradle.java.home=$REAL_JAVA_HOME

FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=build /home/gradle/project/app/build/libs/sll-reverse-between.jar sll-reverse-between.jar
CMD ["java", "-jar", "sll-reverse-between.jar"]
