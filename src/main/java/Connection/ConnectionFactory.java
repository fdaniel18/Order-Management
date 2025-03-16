package Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa care furnizează o conexiune la baza de date și metode pentru închiderea resurselor asociate.
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb";
    private static final String USER = "root";
    private static final String PASS = "1234";

    // O singură instanță a clasei ConnectionFactory
    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    // Constructor privat pentru a preveni instantierea din afara clasei
    private ConnectionFactory() {
        try {
            // Încarcă driverul MySQL
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Driverul MySQL nu a putut fi încărcat.", e);
        }
    }

    /**
     * Creează și returnează o conexiune la baza de date.
     *
     * @return conexiunea la baza de date
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    // Creează o conexiune la baza de date
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Eroare la conectarea la baza de date.", e);
        }
        return connection;
    }

    /**
     * Închide conexiunea la baza de date.
     *
     * @param connection conexiunea de închis
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Eroare la închiderea conexiunii.", e);
            }
        }
    }

    /**
     * Închide obiectul de instrucțiuni.
     *
     * @param statement obiectul de instrucțiuni de închis
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Eroare la închiderea obiectului de instrucțiuni.", e);
            }
        }
    }

    /**
     * Închide obiectul ResultSet.
     *
     * @param resultSet obiectul ResultSet de închis
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Eroare la închiderea obiectului ResultSet.", e);
            }
        }
    }
}

