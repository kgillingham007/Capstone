package gillingham.capstone.Database;

import gillingham.capstone.model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**Used for appointment related connections to the dataabse
 *
 */
public class appointmentDAO {

    /**Used to get all appointments
     *
     * @return the list of all appointments
     * @throws SQLException if there is an issue accessing the database
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException{
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDescription = resultSet.getString("Description");
            String appointmentLocation = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            Appointments appointments = new Appointments(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
            appointmentsObservableList.add(appointments);
        }
        return appointmentsObservableList;
    }

    /**Used to delete the selected appointment
     *
     * @param customer from customer ID
     * @param connection from the database connection
     * @return the updated appointment list
     * @throws SQLException if there is an issue deleting the appointment
     */
    public static int deleteAppointment(int customer, Connection connection) throws SQLException{
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,customer);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result;
    }
}
