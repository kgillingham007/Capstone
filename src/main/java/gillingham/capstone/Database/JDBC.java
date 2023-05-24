package gillingham.capstone.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**Used for connections to the database.
 *
 */
public abstract class JDBC {

    /**Labels for the connections
     *
     */
    private static PreparedStatement preparedStatement;


    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface


    /**Main connection method
     *
     * @return the connection to the database
     */
    public static Connection openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return connection;
    }


    /**Used to close the connection
     *
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }


    /**Used to get the preparedStatement
     *
     * @return the selected preparedStatement
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }


    /**Used to set the preparedStatement
     *
     * @param connection from the database connection
     * @param sqlStatement from the given sqlstatement
     * @throws SQLException if there is an issue accessing the database
     */
    public static void setPreparedStatement(Connection connection , String sqlStatement) throws SQLException {
        //JDBC.preparedStatement = preparedStatement;
        preparedStatement = connection.prepareStatement(sqlStatement);
    }


    /**Used to get the connection to the database
     *
     * @return the connection
     */
    public static Connection getConnection(){
        return connection;
    }


}
