import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_db",
                    "root",
                    "Root123!"
            );
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}

