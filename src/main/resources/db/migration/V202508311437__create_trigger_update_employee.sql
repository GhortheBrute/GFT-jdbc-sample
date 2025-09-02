-- update.sql

DROP TRIGGER IF EXISTS jdbcsample.trg_employees_audit_update;

CREATE TRIGGER jdbcsample.trg_employees_audit_update
BEFORE UPDATE ON jdbcsample.employees
FOR EACH ROW
BEGIN
    INSERT INTO jdbcsample.employees_audit (
        employee_id,
        name,
        old_salary,
        salary,
        old_salary,
        birthday,
        old_birthday,
        operation
    ) VALUES (
        NEW.id,
        NEW.name,
        Old.name,
        NEW.salary,
        Old.salary,
        NEW.birthday,
        OLD.birthday,
        'U'
    );
END;
