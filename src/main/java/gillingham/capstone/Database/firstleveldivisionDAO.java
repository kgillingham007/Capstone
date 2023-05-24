package gillingham.capstone.Database;

import gillingham.capstone.model.firstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class firstleveldivisionDAO extends firstLevelDivision {
    public firstleveldivisionDAO(int divisionID , String Division , int country_ID){
        super (divisionID,Division,country_ID);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<firstleveldivisionDAO> getAllFirstLevelDivisions() throws SQLException{
        ObservableList<firstleveldivisionDAO> firstleveldivisionObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int Division_ID = resultSet.getInt("Division_ID");
            String Division = resultSet.getString("Division");
            int Country_ID = resultSet.getInt("Country_ID");
            firstleveldivisionDAO firstLevelDivision = new firstleveldivisionDAO(Division_ID , Division , Country_ID);
            firstleveldivisionObservableList.add(firstLevelDivision);
        }
        return firstleveldivisionObservableList;
    }

    public static firstLevelDivision getDivisionFromDivision(firstLevelDivision FirstLevelDivision) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division  = '" + FirstLevelDivision + "'";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        //Query.makeQuery(sqlStatement);
        firstLevelDivision divisionResultFromDivision;
        ResultSet resultset = preparedStatement.executeQuery();
        while (resultset.next()) {
            int Division_ID = resultset.getInt("Division_ID");
            String Division = resultset.getString("Division");
            //Timestamp Create_Date = resultset.getTimestamp("Create_Date");
            //LocalDateTime createDateCalendar = Create_Date.toLocalDateTime();
            //String Created_By = resultset.getString("Created_By");
            //Timestamp Last_Update = resultset.getTimestamp("Last_Update");
            //LocalDateTime lastUpdateCalendar = Last_Update.toLocalDateTime();
            //String Last_Updated_By = resultset.getString("Last_Updated_By");
            int Country_ID = resultset.getInt("Country_ID");

            //divisionResult = new firstLevelDivision(Division_ID, Division, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By, COUNTRY_ID);
            divisionResultFromDivision = new firstLevelDivision(Division_ID, Division, Country_ID);
            return divisionResultFromDivision;
        }
        return null;
    }

    public static ObservableList<firstLevelDivision> getDivisionFromCountryID(int country_id) throws SQLException {
        ObservableList<firstLevelDivision> divisionFromCountryID = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + country_id;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        //Query.makeQuery(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            int Division_ID = result.getInt("Division_ID");
            String Division = result.getString("Division");
            /*Timestamp Create_Date = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = Create_Date.toLocalDateTime();
            String Created_By = result.getString("Created_By");
            Timestamp Last_Update = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = Last_Update.toLocalDateTime();
            String Last_Updated_By = result.getString("Last_Updated_By");*/
            int Country_ID = result.getInt("Country_ID");

            //firstLevelDivision divResult = new firstLevelDivision(Division_ID, Division, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By, COUNTRY_ID);
            firstLevelDivision divisionResultFromCountryID = new firstLevelDivision(Division_ID, Division, Country_ID);
            divisionFromCountryID.add(divisionResultFromCountryID);
        }
        return divisionFromCountryID;
    }

    public static firstLevelDivision getDivisionFromDivisionID(int division_id) throws SQLException {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE DIVISION_ID = " + division_id;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        //Query.makeQuery(sqlStatement);
        firstLevelDivision DivisionFromDivisionID;
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            int Division_ID = result.getInt("Division_ID");
            String Division = result.getString("Division");
            /*Timestamp Create_Date = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = Create_Date.toLocalDateTime();
            String Created_By = result.getString("Created_By");
            Timestamp Last_Update = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = Last_Update.toLocalDateTime();
            String Last_Updated_By = result.getString("Last_Updated_By");*/
            int Country_ID = result.getInt("Country_ID");

            //getDivFromDivIDResult = new firstLevelDivision(Division_ID, Division, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By, COUNTRY_ID);
            DivisionFromDivisionID = new firstLevelDivision(Division_ID, Division, Country_ID);
            return DivisionFromDivisionID;
        }
        return null;
    }
}