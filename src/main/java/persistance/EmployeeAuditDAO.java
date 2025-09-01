package persistance;

import persistance.entity.EmployeeAuditEntity;
import persistance.entity.EmployeeEntity;
import persistance.entity.OperationEnum;

import java.math.BigDecimal;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;

public class EmployeeAuditDAO {

    private static final String FIND_ALL_AUDIT_SQL = """
            SELECT * FROM view_employee_audit
            """;


    public List<EmployeeAuditEntity> findAll() {
        List<EmployeeAuditEntity> employees = new ArrayList<>();
        try(
                Connection conn = ConnectionUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ALL_AUDIT_SQL);
        ){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employees.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não conseguiu listar.", e);
        }
        return employees;
    }

    private static EmployeeAuditEntity mapRow(ResultSet rs) throws SQLException {
        Timestamp birthdayTs = rs.getTimestamp("birthday");
        Timestamp oldBirthdayTs = rs.getTimestamp("old_birthday");
        OffsetDateTime birthday = toOffsetDateTime(birthdayTs);
        OffsetDateTime oldBirthday = toOffsetDateTime(oldBirthdayTs);

        return new EmployeeAuditEntity(
                rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("old_name"),
                    rs.getBigDecimal("salary"),
                    rs.getBigDecimal("old_salary"),
                    birthday,
                    oldBirthday,
                    OperationEnum.getByOperation(rs.getString("operation"))
        );
    }


    private static OffsetDateTime toOffsetDateTime(Timestamp ts) {
        return ts == null ? null : ts.toInstant().atOffset(UTC);
    }

}
