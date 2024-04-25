import com.example.myproject.config.DatabaseConnection;
import com.example.myproject.config.DatabaseConnection;

import java.sql.Connection;

public class TestConnessioneDatabase {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnessione();
        if (connection != null) {
            System.out.println("Connessione al database riuscita!");
        } else {
            System.out.println("Connessione al database fallita.");
        }
    }
}
