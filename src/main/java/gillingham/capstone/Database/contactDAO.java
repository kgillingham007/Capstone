package gillingham.capstone.Database;

import gillingham.capstone.model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Used for contact related connections to the dataabse
 *
 */
public class contactDAO {

    /**Used to get all Contacts
     *
     * @return the list of all contacts
     * @throws SQLException if there is an issue accessing the database
     */
    public static ObservableList<Contacts> getAllContacts() throws SQLException{
        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String contactEmail = resultSet.getString("Email");
            Contacts contacts = new Contacts(contactID, contactName, contactEmail);
            contactsObservableList.add(contacts);
        }
        return contactsObservableList;
    }

    /**Used to find the contact ID
     *
     * @param contactID
     * @return the appropriate contact ID
     * @throws SQLException if there is an issue accessing the database
     */
    public static String findContactID(String contactID) throws SQLException{
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?");
        preparedStatement.setString(1,contactID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            contactID = resultSet.getString("Contact_ID");
        }
        return contactID;
    }
}