CREATE TABLE employees(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    age TIMESTAMP NOT NULL
)engine=INNODB default charset=utf8;