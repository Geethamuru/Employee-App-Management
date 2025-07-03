Create Database EmployeeDB;
Use EmployeeDB;

Create Table employees(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100),
email VARCHAR(100),
salary DOUBLE);