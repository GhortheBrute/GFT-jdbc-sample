CREATE VIEW view_employee_audit AS
    SELECT employee_id,
           salary,
           old_salary,
           birthday,
           old_birthday,
           operation
    FROM employees_audit
    ORDER BY created_at;