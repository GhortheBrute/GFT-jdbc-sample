package persistance;

import persistance.entity.ContactEntity;
import persistance.entity.EmployeeEntity;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.TimeZone.LONG;

public class EmployeeDAO {
    private static final String INSERT_EMPLOYEE_SQL = """
                INSERT INTO employees (name, salary, birthday)
                VALUES (?, ?, ?)
            """;

    private static final String UPDATE_EMPLOYEE_SQL = """
                UPDATE employees SET name = ?, salary = ?, birthday = ? WHERE id = ?
            """;

    private static final String FIND_ALL_EMPLOYEE_SQL = """
                SELECT * FROM employees
            """;

    private static final String DELETE_EMPLOYEE_SQL = """
                DELETE FROM employees WHERE id = ?
            """;

    private static final String FIND_BY_ID_EMPLOYEE_SQL = """
                SELECT * FROM employees WHERE id = ?
            """;

    private static final String INSERT_WITH_PROCEDURE_EMPLOYEE_SQL = """
                CALL prc_insert_employee(?, ?, ?, ?)
            """;

    public void insert(final EmployeeEntity employee) {
        validateEmployee(employee);

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            bindEmployeeForInsert(statement, employee);

            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    employee.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário", e);
        }
    }

    public void update(EmployeeEntity employee) {
        validateEmployee(employee);

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE_SQL)
        ) {
            bindEmployeeForUpdate(statement, employee);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário", e);
        }
    }

    public void delete(final long id) {
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_SQL)
        ){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar um funcionário com ID: " + id, e);
        }

        System.out.println("Funcionário deletado com sucesso");
    }

    public List<EmployeeEntity> findAll() {
        List<EmployeeEntity> employees = new ArrayList<>();
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_EMPLOYEE_SQL)
        ) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionários: ", e);
        }
        return employees;
    }

    public EmployeeEntity findById(Long employeeId) {
    if (employeeId == null || employeeId <= 0) {
        throw new IllegalArgumentException("employeeId must be a positive non-null value");
    }
    try (
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_EMPLOYEE_SQL)

    ) {
        statement.setLong(1, employeeId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return mapRow(rs);
        }
        return null;
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar funcionário (id=" + employeeId + "): ", e);
    }
}

    public void insertWithProcedure (EmployeeEntity employee) {
        validateEmployee(employee);

        try(
                Connection conn = ConnectionUtil.getConnection();
                CallableStatement stmt = conn.prepareCall(INSERT_WITH_PROCEDURE_EMPLOYEE_SQL)
                ){
            stmt.registerOutParameter(1, LONG);
            stmt.setString(2, employee.getName());
            stmt.setBigDecimal(3, employee.getSalary());
            stmt.setTimestamp(4, toTimestamp(employee.getBirthdate()));
            stmt.execute();
            employee.setId(stmt.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException("Problema em incluir funcionário com procedimento", e);
        }
    }

    public void insertBatch(final List<EmployeeEntity> list) {
        validateEmployeesList(list);

        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE_SQL, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                for (var employee : list) {
                    bindEmployeeForInsert(statement, employee);
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Erro ao inserir funcionário", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public void insertContact(final EmployeeEntity employee, final ContactEntity contact) {

    }

    private static Timestamp toTimestamp(final OffsetDateTime birthdate) {
        // Defensive check, though we validate earlier
        if (birthdate == null) {
            throw new IllegalArgumentException("birthdate must not be null");
        }
        return Timestamp.from(birthdate.toInstant());
    }

    private static EmployeeEntity mapRow(final ResultSet rs) throws SQLException {
        var entity = new EmployeeEntity();
        entity.setId(rs.getLong("id"));
        entity.setName(rs.getString("name"));
        entity.setSalary(rs.getBigDecimal("salary"));
        Timestamp ts = rs.getTimestamp("birthday");
        entity.setBirthdate(toOffsetDateTime(ts));
        return entity;
    }

    private static OffsetDateTime toOffsetDateTime(final Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toInstant().atOffset(java.time.ZoneOffset.UTC);
    }

    private static void bindEmployeeForInsert(PreparedStatement ps, EmployeeEntity e) throws SQLException {
        // INSERT employees(name=?, salary=?, birthday=?)
        ps.setString(1, e.getName());
        ps.setBigDecimal(2, e.getSalary());
        ps.setTimestamp(3, toTimestamp(e.getBirthdate()));
    }

    private static void bindEmployeeForUpdate(PreparedStatement ps, EmployeeEntity e) throws SQLException {
        // UPDATE employees SET name=?, salary=?, birthday=? WHERE id=?
        ps.setString(1, e.getName());
        ps.setBigDecimal(2, e.getSalary());
        ps.setTimestamp(3, toTimestamp(e.getBirthdate()));
        ps.setLong(4, e.getId());
    }

    private static void validateEmployee(EmployeeEntity employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        if (employee.getName() == null) {
            throw new IllegalArgumentException("Nome de funcionário não informado.");
        }
        if (employee.getSalary() == null) {
            throw new IllegalArgumentException("Salário do funcionário " + employee.getName() + " não informado.");
        }
        if (employee.getBirthdate() == null) {
            throw new IllegalArgumentException("Aniversário do funcionário " + employee.getName() + " não informado.");
        }
    }

    private static void validateEmployeesList(List<EmployeeEntity> list) {
        for (EmployeeEntity employee : list) {
            validateEmployee(employee);
        }
    }
}