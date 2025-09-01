CREATE TABLE employees_audit(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    old_name VARCHAR(255),
    salary DECIMAL(10,2),
    old_salary DECIMAL(10,2),
    birthday TIMESTAMP,
    old_birthday TIMESTAMP,
    operation CHAR(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)engine=InnoDB default charset=utf8mb4;