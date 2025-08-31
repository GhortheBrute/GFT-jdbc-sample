package persistance;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ConnectionUtil {



    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = ConnectionUtil.class.getResourceAsStream("/gradle.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar db.properties", e);
        }

        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");

        return DriverManager.getConnection("jdbc:mysql://localhost/jdbcsample",dbUser,dbPassword);
    }
}