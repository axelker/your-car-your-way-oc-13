# Your car your way System

![YCYW image](./resources/logo.png)

Your Car Your Way is an international car rental platform. This project represents the new unified version of the client portal, integrating modern features such as authentication, customer support (messaging + video call), and rental management.
The goal of this application is to provide users with a single, streamlined space to:

- Register and log in securely
- Manage vehicle rentals
- Contact customer support via **real-time messaging** and **video calls**



---

## Table of Contents

- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Real-Time Communication](#real-time-communication)
- [API Documentation](#api-documentation)

---
## ⚠️ Current Status – POC Phase

At this stage, the application includes a functional **Proof of Concept (POC)** for:

- 🔐 **Authentication** (Login, Register)
- 💬 **Customer Support Chat** using WebSocket + STOMP + RxStomp
- 📹 **Video Call** using WebRTC + STUN + signaling over STOMP

These modules demonstrate real-time interaction but are **not yet finalized for production**.

The POC focuses on:
- Technical validation of the chosen architecture.
- Demonstrating key features for internal feedback.
- Preparing the ground for full integration with the rest of the rental and reservation system.

> Next steps: Finalize security, session handling, TURN fallback, and UI/UX refinement.
---

## Requirements

Before running this project, make sure you have the following installed:

- **Java 17** → This project requires Java 17 as the runtime environment. Ensure you have it installed. [Download](https://jdk.java.net/archive/)
- **Maven** → Used for managing project dependencies and building the application. [Installation Guide](https://maven.apache.org/install.html)
- **MySQL** → The backend database for storing application data. [Setup Instructions](https://openclassrooms.com/fr/courses/6971126-implementez-vos-bases-de-donnees-relationnelles-avec-sql/7152681-installez-le-sgbd-mysql)
- **Node.js** → Node.js is required to run the Angular development server and install dependencies. [Download](https://nodejs.org/en/download)


---

## Getting Started

Follow these steps to set up and run the project.

### **Backend**

### Dependencies

#### Configure Java Environment Variables

#### Windows

1. Open Command Prompt and run:
   ```sh
   echo %JAVA_HOME%
   ```
   If nothing is displayed, proceed with the following steps:
2. Open **System Properties** > **Advanced** > **Environment Variables**
3. Under **System Variables**, click **New** and add:
   - **Variable name**: `JAVA_HOME`
   - **Variable value**: `C:\Program Files\Java\jdk-17`
4. Add `%JAVA_HOME%\bin` to the **Path** variable.

### Installing

#### Initialize the Database

#### 1. Login to MySQL

Open a terminal.

```sh
mysql -u root -p
```

(Enter your MySQL root password when prompted)

#### 2. Run the SQL script

Find the script inside the resources of the project.

```sh
source path/to/create_database.sql;
```
By default, the database is initialized with two users one client and on support.

Client User Credentials:
✉️ Email: test@test.com
🔑 Password: test!1234

Support User Credentials:
✉️ Email: test-support@test.com
🔑 Password: test!1234

### Executing program

#### Compile the project

```sh
mvn clean package
```

#### Run the application

```sh
 mvn spring-boot:run
```

The application will be available at **[http://localhost:8080](http://localhost:8080)**.


### Frontend

#### Installing Frontend Dependencies
Navigate to the frontend project directory.
Install the required dependencies using npm (Node Package Manager):
```sh 
   npm install
```
This will install all the necessary packages defined in the package.json file.

#### Development Server
After installing dependencies, you can start the frontend development server:

Run the following command to start the development server:
```sh
npm start
```
The application will be available at http://localhost:4200.
The development server will automatically reload if you change any of the source files.


---

## Real-Time Communication

Our application uses **WebSocket**, **STOMP**, and **WebRTC** to enable responsive, real-time interaction between users and the support team.

---

### 💬 Chat: WebSocket + STOMP + RxStomp

- We use **WebSocket** for persistent, bidirectional communication.
- **STOMP** (Simple Text Oriented Messaging Protocol) is layered on top to structure messages with headers and destinations (topics).
- The frontend uses **RxStomp**, a reactive WebSocket client that integrates with **RxJS** to manage observable message streams and clean lifecycle handling.

✅ This architecture enables **reliable, scalable messaging** between users and support agents.

---

### 🎥 Visio: WebRTC + STUN + Signaling via STOMP

For video calls, we use the **WebRTC protocol**:

1. Each peer collects its **ICE candidates** (network info) using **STUN** servers.
2. Peers exchange **SDP offers/answers** and **ICE candidates** via **WebSocket + STOMP** — this process is called **signaling**.
3. Once both peers have the required info, a **direct peer-to-peer (UDP)** connection is established for video/audio.

---

## API Documentation

The API documentation is available via **Swagger UI** at:

📌 **[http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)**


---