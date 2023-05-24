package gillingham.capstone.Database;

import gillingham.capstone.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Used for country related connections to the database
 *
 */
public class countryDAO extends Country {

    /** used to set the country DAO
     *
     * @param countryID from country ID
     * @param countryName from country Name
     */
    public countryDAO (int countryID, String countryName){
        super(countryID, countryName);
    }

    /**used to get all Countries
     *
     * @return the list of all countries
     * @throws SQLException if there is an issue accessing the database
     */
    public static ObservableList<countryDAO> getCountries() throws SQLException{
        ObservableList<countryDAO> countriesObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country from countries";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int countryID = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");
            countryDAO country = new countryDAO(countryID , countryName);
            countriesObservableList.add(country);
        }
        return countriesObservableList;
    }


    /**Used to get country from Country ID
     *
     * @param CountryID from country ID
     * @param divisionID from division ID
     * @return country based on parameters
     * @throws SQLException if there is an issue with database
     * @throws Exception any other issues
     */
    public static Country getCountryFromCountryID(int CountryID, int divisionID) throws SQLException, Exception{
        //String sqlStatement="SELECT * FROM countries WHERE Country_ID  = '" + CountryID + "'";
        String sqlStatement = "SELECT * FROM countries WHERE Country_ID = "
                + "(SELECT Country_ID FROM first_level_divisions WHERE Division_ID = " + divisionID + ")";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        //Query.makeQuery(sqlStatement);
        Country countryResult;
        ResultSet result=preparedStatement.executeQuery();
        while(result.next()){
            int Country_ID=result.getInt("Country_ID");
            String Country=result.getString("Country");
            /*Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");*/

            //countryResult= new Country(Country_ID, Country, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By);
            countryResult= new Country(Country_ID, Country);
            return countryResult;
        }
        return null;
    }
}
