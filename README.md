# 🚗 Car Rental Management Application

![Car Rental Banner](https://www.topgear.com/sites/default/files/news-listicle/image/2018/01/ts4_1616.jpg)  
*A web application for managing car rentals, built with **Spring Boot** and **MySQL**, deployable using Docker Compose.*

---

## ✨ Features

- 🚘 **Car Management** – Manage vehicles in the rental fleet
- 📅 **Reservation System** – Bookings & scheduling system
- 👥 **User-Friendly Interface** – Smooth experience for clients

---

## 🛠️ Prerequisites

Make sure you have the following installed:

- 🐳 [Docker](https://www.docker.com/get-started)
- ⚙️ [Docker Compose](https://docs.docker.com/compose/)

---

## 🚀 Quick Start

1. **📧 Configure Spring Mail** in `application.properties`
   ```properties
   spring.mail.username=your-email@example.com
   spring.mail.password=your-password
   ```

2. **▶️ Run the application**
   ```bash
   docker-compose up --build
   ```

---

## ⚙️ Configuration

- 🔑 **Database Settings** → Defined in `docker-compose.yml` & Spring Boot configs
- 📧 **Email Service** → Needs valid credentials (as above)

---

## 💻 Development Runtime

1. **⚙️ Requirements**
- Maven
- Java 21
- Docker & Docker Compose

2. **📂 Clone the repository**
   ```bash
   git clone https://github.com/DerZiad/CarRental.git
   cd CarRental
   ```

3. **🐬 Start MySQL service first**
   ```bash
   docker-compose up mysql -d
   ```

4. **📦 Start the full application (under `dev` profile)**
   ```bash
   mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
   ```

   This will:
    - Launch **MySQL database**
    - Launch **Spring Boot backend** (running under the `dev` profile)

5. **🌐 Access the application**
    - Backend API → [http://localhost:8080](http://localhost:8080)
    - MySQL → `localhost:3306` (credentials in `docker-compose.yml`)

---

## 🏆 Project Background

This project was developed as part of a **university coding challenge** at *Moulay Ismail University*.  
It provides rental agencies with tools to manage operations efficiently:

- 🚗 Vehicle inventory management
- 📅 Reservation & booking handling
- 👥 Client administration

🏅 **Awarded First Prize** for technical excellence and practical business impact.

---

## 📖 Project Description

This project is a robust car rental management platform, designed and implemented as part of a university coding challenge at Moulay Ismail University.  
The solution empowers car rental businesses to streamline their operations, offering advanced features for vehicle inventory management, reservation processing, and client administration.

Developed with a focus on scalability and reliability, the application leverages modern technologies including **Spring Boot** and **MySQL**, and is fully containerized for seamless deployment.

The project was recognized with the **first prize** for its technical excellence and practical impact. It demonstrates a commitment to delivering professional-grade software solutions that address real-world business needs in the automotive rental sector.

---

## 📜 License

This project is licensed under the **MIT License**.

---

## 💡 Tech Stack

Here are the main technologies and skills used in this project:

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white&style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white&style=for-the-badge)
![Java](https://img.shields.io/badge/Java-007396?logo=java&logoColor=white&style=for-the-badge)
![JSP](https://img.shields.io/badge/JSP-FF6F00?logo=java&logoColor=white&style=for-the-badge)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black&style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white&style=for-the-badge)
