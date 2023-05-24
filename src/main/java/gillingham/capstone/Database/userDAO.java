package gillingham.capstone.Database;

import gillingham.capstone.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Used for user related database connections
 *
 */
public class userDAO extends User {

    /**Used to set a new userDAO
     *
     * @param userID
     * @param userName
     * @param userPassword
     */
    public userDAO(int userID,String userName,String userPassword){
        super();
    }

    /**Used to validate the user logging in
     *
     * @param username from login screen
     * @param password from login screen
     * @return if the user meets the requirements to login or not
     */
    /*public static int validateUser(String username , String password){
        try {
            String sqlQuery = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password +"'";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getString("User_Name").equals(username)){
                if (resultSet.getString("Password").equals(password)){
                    return resultSet.getInt("User_ID");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }*/

    public static int validateUser(String username , String password){
        try {
            String sqlQuery = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password +"'";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst() ) {
                // if there are no rows in the ResultSet, return -1
                return -1;
            }
            else {
                resultSet.next();
                if (resultSet.getString("User_Name").equals(username)){
                    if (resultSet.getString("Password").equals(password)){
                        return resultSet.getInt("User_ID");
                    }
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }




    /**Used to get all Users
     *
     * @return the list of all users
     * @throws SQLException if there is an issue accessing the database
     */
    public static ObservableList<userDAO> getAllUsers() throws SQLException{
        ObservableList<userDAO> userDAOObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int userID = resultSet.getInt("User_ID");
            String userName = resultSet.getString("User_Name");
            String userPassword = resultSet.getString("Password");
            userDAO user = new userDAO(userID,userName,userPassword);
            userDAOObservableList.add(user);
        }
        return userDAOObservableList;
    }
}
