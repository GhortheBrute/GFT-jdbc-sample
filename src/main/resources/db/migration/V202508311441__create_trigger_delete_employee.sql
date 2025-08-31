-- update.sql

DROP TRIGGER IF EXISTS jdbcsample.trg_employees_audit_delete;

CREATE TRIGGER jdbcsample.trg_employees_audit_delete
BEFORE DELETE ON jdbcsample.employees
FOR EACH ROW
BEGIN
    INSERT INTO jdbcsample.employees_audit (
        employee_id,
        name,
        salary,
        birthday,
        operation
    ) VALUES (
        OLD.id,
        Old.name,
        Old.salary,
        OLD.age,
        'D'
    );
END;
