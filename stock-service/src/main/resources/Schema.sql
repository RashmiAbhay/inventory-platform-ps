CREATE DATABASE stockdb;

USE stockdb;

CREATE TABLE item_stock (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            item_id VARCHAR(50) NOT NULL UNIQUE,
                            item_name VARCHAR(255),
                            available_stock INT NOT NULL,
                            reserved_stock INT DEFAULT 0,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                ON UPDATE CURRENT_TIMESTAMP
);