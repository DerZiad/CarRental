FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

COPY . /app

# Install netcat for wait-for-mysql.sh
RUN apt-get update && apt-get install -y netcat

COPY wait-for-mysql.sh /wait-for-mysql.sh
RUN chmod +x /wait-for-mysql.sh

RUN mvn clean compile package -DskipTests

CMD ["/wait-for-mysql.sh", "mysql", "3306", "java", "-jar", "target/car_rental-0.0.1-SNAPSHOT.war"]
