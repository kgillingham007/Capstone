package gillingham.capstone.Database;

import gillingham.capstone.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Used for customer related connections to the database
 *
 */
public class customerDAO {

    /**Used to get all customers
     *
     * @param connection to the database
     * @return the list of all customers
     * @throws SQLException if there is an issue accessing the database
     */
    public static ObservableList<Customer> getAllCustomers(Connection connection) throws SQLException{
        String query = "SELECT customers.Customer_ID , customers.Customer_Name , customers.Address , customers.Postal_Code , customers.Phone , customers.Division_ID , first_level_divisions.Division from customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
        //String sqlQuery = "SELECT * FROM customers";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        while (resultSet.next()){
            int customerID = resultSet.getInt("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String customerAddress = resultSet.getString("Address");
            String customerPostalCode = resultSet.getString("Postal_Code");
            String customerPhone = resultSet.getString("Phone");
            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            Customer customer = new Customer(customerID,customerName,customerAddress,customerPostalCode,customerPhone,divisionName,divisionID);
            customerObservableList.add(customer);
        }
        return customerObservableList;
    }
}
