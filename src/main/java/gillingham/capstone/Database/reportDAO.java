package gillingham.capstone.Database;

import gillingham.capstone.model.Appointments;
import gillingham.capstone.model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** Used for report related connections
 *
 */
public class reportDAO extends Appointments {

    /**Used to set a new reportDAO
     *
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public reportDAO(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID){
        super (appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentTitle, start, end, customerID, userID, customerID);
    }

    /**Used to get all countries
     *
     * @return the list of all countries
     * @throws SQLException if there is an issue accessing the database
     */
    public static ObservableList<Report> getCountries() throws SQLException{
        ObservableList<Report> countriesObservableList = FXCollections.observableArrayList();
        String sql = "SELECT countries.Country , count(*) as countryCount from customers INNER JOIN first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries on countries.Country_ID = first_level_divisions.Country_ID where customers.Division_ID = first_level_divisions.Division_ID group by first_level_divisions.Country_ID order by count(*) desc";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String countryName = resultSet.getString("Country");
            int countryCount = resultSet.getInt("countryCount");
            Report report = new Report(countryName,countryCount);
            countriesObservableList.add(report);
        }
        return countriesObservableList;
    }
}
