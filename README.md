# Your car your way System

![YCYW image](./resources/logo.png)

Your Car Your Way is an international car rental platform. This project represents the new unified version of the client portal, integrating modern features such as authentication, customer support (messaging + video call), and rental management.
The goal of this application is to provide users with a single, streamlined space to:

- register and log in securely

- contact customer support via chat or video

---

## Table of Contents

- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)

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

## API Documentation

The API documentation is available via **Swagger UI** at:

📌 **[http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)**


---
