CREATE TABLE contacts(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(30) NOT NULL,
    employee_id BIGINT NOT NULL,
    CONSTRAINT fk_employee_id
    FOREIGN KEY (employee_id) REFERENCES employees(id)
)engine = InnoDB default charset = utf8mb4;