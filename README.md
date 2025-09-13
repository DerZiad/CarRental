# Car Rental Management Application

A web application for car rental management, built with Spring Boot and MySQL. Easily deployable using Docker Compose.

## Features

- Car management for rental agencies
- Reservation system
- User-friendly client interface

## Prerequisites

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)

## Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/DerZiad/CarRental.git
   cd CarRental
   ```

2. **Start the application with Docker Compose**
   ```bash
   docker-compose up --build
   ```

   This will start both the MySQL database and the Spring Boot server.

3. **Access the application**

   - The backend API will be available at: [http://localhost:8080](http://localhost:8080)
   - MySQL will be available at: `localhost:3306` (see `docker-compose.yml` for credentials)

## Configuration

- Database credentials and other settings can be adjusted in `docker-compose.yml` and the Spring Boot configuration files.
- **Spring Mail must be configured:**  
  Set your email credentials in `application.properties` for `spring.mail.username` and `spring.mail.password` to enable email features.

## Development

- To run locally without Docker, ensure you have Java 21 and Maven installed.
- Build and run with:
  ```bash
  mvn clean package
  java -jar server/target/server.jar
  ```

## License

MIT
