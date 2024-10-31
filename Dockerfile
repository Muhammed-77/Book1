#Use a Maven image with OpenJDK 17 as the base image
FROM maven:3.8.3-openjdk-17


#Set the working directory in the container
WORKDIR /app

#Copy the Maven build files and project source code to the container
COPY . .

#Package the application and skip tests
RUN mvn clean package -DskipTests

#Find any JAR file in the target directory and rename it to server.jar
RUN cp target/*.jar server.jar

#Expose the port that the app will run on
EXPOSE 8080

#Run the JAR file with the specified port
ENTRYPOINT ["java", "-jar", "server.jar", "--server.port=8081"]
