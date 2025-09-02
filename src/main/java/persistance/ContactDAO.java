package persistance;

import persistance.entity.ContactEntity;
import persistance.entity.EmployeeEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDAO {
    private static final String INSERT_CONTACT_SQL = """
                INSERT INTO contacts (description, type, employee_id)
                VALUES (?, ?, ?)
            """;

    public void insert(final ContactEntity contact) {
        validateContact(contact);

        try (
                Connection conn = ConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_CONTACT_SQL)
                ){
            stmt.setString(1, contact.getDescription());
            stmt.setString(2, contact.getType());
            stmt.setLong(3, contact.getEmployee().getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir um contato.", e);
        }
    }

    private static void validateContact(ContactEntity contact) {
        if (contact == null) {
            throw new IllegalArgumentException("contact must not be null");
        }
        if (contact.getDescription() == null) {
            throw new IllegalArgumentException("contact.description must not be null");
        }
        if (contact.getType() == null) {
            throw new IllegalArgumentException("contact.type must not be null");
        }
    }
}
