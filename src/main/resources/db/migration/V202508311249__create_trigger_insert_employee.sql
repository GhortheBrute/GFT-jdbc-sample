-- V2__create_trigger.sql

DROP TRIGGER IF EXISTS jdbcsample.trg_employees_audit_ai;

CREATE TRIGGER jdbcsample.trg_employees_audit_ai
AFTER INSERT ON jdbcsample.employees
FOR EACH ROW
BEGIN
    INSERT INTO jdbcsample.employees_audit (
        employee_id,
        name,
        salary,
        birthday,
        operation
    ) VALUES (
        NEW.id,
        NEW.name,
        NEW.salary,
        NEW.birthday,
        'I'
    );
END;
