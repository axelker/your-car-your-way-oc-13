DROP DATABASE IF EXISTS `ycyw-oc-project-13`;

CREATE DATABASE `ycyw-oc-project-13`;

USE `ycyw-oc-project-13`;

DROP TABLE IF EXISTS support_visio_logs;
DROP TABLE IF EXISTS support_requests;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS rentals;
DROP TABLE IF EXISTS agencies;
DROP TABLE IF EXISTS vehicles;
DROP TABLE IF EXISTS supports;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('CLIENT', 'AGENT', 'SUPPORT') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);
CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street_name VARCHAR(255) NOT NULL,
    street_number INT NOT NULL,
    country VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    postcode INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE profiles (
    user_id BIGINT PRIMARY KEY,
    address_id BIGINT NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    birthdate DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE

);

CREATE TABLE support_infos (
    user_id BIGINT PRIMARY KEY,
    lastname VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE vehicles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    acriss_code VARCHAR(4) NOT NULL,
    category VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    transmission VARCHAR(255) NOT NULL,
    fuel VARCHAR(255) NOT NULL,
    aircon boolean NOT NULL,
    year int NOT NULL,
    brand VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE agencies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE
);

CREATE TABLE rentals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    agency_arrival_id BIGINT NOT NULL,
    agency_departure_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    departure_date_time TIMESTAMP NOT NULL,
    arrival_date_time TIMESTAMP NOT NULL,
    price FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (agency_arrival_id) REFERENCES agencies(id) ON DELETE CASCADE,
    FOREIGN KEY (agency_departure_id) REFERENCES agencies(id) ON DELETE CASCADE,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE,
    FOREIGN KEY (profile_id) REFERENCES profiles(user_id) ON DELETE CASCADE
);

CREATE TABLE conversations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE conversation_participants (
    user_id BIGINT NOT NULL,
    conversation_id BIGINT NOT NULL,
    joined_at TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id,conversation_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE
);

CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    conversation_id BIGINT NOT NULL,
    content VARCHAR(2500) NOT NULL,
    sent_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE
);

CREATE TABLE support_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subject VARCHAR(100) NOT NULL,
    status ENUM('progress', 'open', 'closed') NOT NULL,
    sent_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE support_visio_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    user_support_id BIGINT NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (user_support_id) REFERENCES users(id) ON DELETE CASCADE
);



INSERT INTO users (email, password,role) 
VALUES 
('test@test.com', '$2a$10$Cugtb5QEITQsbHMuuBWKqecku/5hup5afBWrVqJdU6nN9Ov/wNYy2','CLIENT'),
('test-support@test.com','$2a$10$Cugtb5QEITQsbHMuuBWKqecku/5hup5afBWrVqJdU6nN9Ov/wNYy2','SUPPORT');

INSERT INTO addresses (street_name, street_number, country, state, postcode)
VALUES ('Main Street', 12, 'France', 'Île-de-France', 75000);

INSERT INTO profiles (user_id, address_id, lastname, firstname, birthdate)
VALUES (1, 1, 'Doe', 'John', '1995-08-20');
INSERT INTO support_infos (user_id, lastname, firstname)
VALUES (2, 'Smith', 'Jane');