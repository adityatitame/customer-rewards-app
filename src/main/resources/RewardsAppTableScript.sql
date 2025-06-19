DROP DATABASE IF EXISTS rewards_app_db;
CREATE DATABASE rewards_app_db;
USE rewards_app_db;

CREATE TABLE customer (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    contact VARCHAR(15) NOT NULL,
    total_reward_points INT DEFAULT 0
);

CREATE TABLE transaction (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    customer_id BIGINT,
    reward_points INT,
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

INSERT INTO customer (name, email, contact, total_reward_points) VALUES
('Aditya Titame', 'aditya@gmail.com', '9876543210', 340),
('Neha Patil', 'neha@gmail.com', '9123456789', 119),
('Rohit Sharma', 'rohit@gmail.com', '9988776655', 155),
('Sneha More', 'sneha@gmail.com', '7896541230', 190),
('Rahul Deshmukh', 'rahul@gmail.com', '9012345678', 120);

INSERT INTO transaction (date, amount, customer_id, reward_points) VALUES
('2025-04-15', 120.00, 1, 90),
('2025-05-10', 200.00, 1, 250),
('2025-06-01', 45.00, 1, 0),

('2025-04-01', 99.00, 2, 49),
('2025-05-15', 110.00, 2, 70),
('2025-06-05', 50.00, 2, 0),

('2025-04-20', 60.00, 3, 10),
('2025-05-25', 130.00, 3, 110),
('2025-06-18', 85.00, 3, 35),

('2025-04-10', 150.00, 4, 150),
('2025-05-18', 90.00, 4, 40),

('2025-04-08', 70.00, 5, 20),
('2025-06-20', 125.00, 5, 100);

SELECT * FROM customer;
SELECT * FROM transaction;
