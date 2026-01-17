-- Student Result Management System Database Setup

-- Create database
CREATE DATABASE IF NOT EXISTS student_db;

-- Use the database
USE student_db;

-- Create student_results table
CREATE TABLE IF NOT EXISTS student_results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    roll_number VARCHAR(20) UNIQUE NOT NULL,
    subject1 INT NOT NULL CHECK (subject1 >= 0 AND subject1 <= 100),
    subject2 INT NOT NULL CHECK (subject2 >= 0 AND subject2 <= 100),
    subject3 INT NOT NULL CHECK (subject3 >= 0 AND subject3 <= 100),
    total INT NOT NULL,
    percentage FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data (optional)
INSERT INTO student_results (name, roll_number, subject1, subject2, subject3, total, percentage) VALUES
('John Doe', 'CS001', 85, 90, 78, 253, 84.33),
('Jane Smith', 'CS002', 92, 88, 95, 275, 91.67),
('Mike Johnson', 'CS003', 76, 82, 79, 237, 79.00);

-- Display the table structure
DESCRIBE student_results;

-- Show sample data
SELECT * FROM student_results;