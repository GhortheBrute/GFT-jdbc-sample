-- Verifica se a coluna já existe
SET @exists := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_NAME = 'employees_audit'
    AND COLUMN_NAME = 'employee_id'
    AND TABLE_SCHEMA = 'jdbcsample'
);

-- Se não existir, adiciona e reposiciona
SET @sql := IF(@exists = 0,
  'ALTER TABLE employees_audit ADD employee_id BIGINT NOT NULL;',
  'SELECT "Column already exists, skipping ADD.";'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Reposiciona a coluna (opcional, só se quiser garantir a ordem)
SET @sql := IF(@exists = 0,
  'ALTER TABLE employees_audit CHANGE COLUMN employee_id employee_id BIGINT NOT NULL AFTER id;',
  'SELECT "Column already exists, skipping CHANGE.";'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
