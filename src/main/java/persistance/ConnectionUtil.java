package persistance;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ConnectionUtil {
    public static Connection getConnection() throws SQLException {
        String user = System.getenv("DB_MYSQL_USER");
        String password = System.getenv("DB_MYSQL_PASSWORD");
        return DriverManager.getConnection("jdbc:mysql://localhost/jdbcsample?useUnicode=true&characterEncoding=UTF-8",user,password);
    }
}