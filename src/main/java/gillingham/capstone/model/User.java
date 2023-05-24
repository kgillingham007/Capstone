package gillingham.capstone.model;

/**User model class
 *
 */
public class User {
    /**User identifiers
     *
     */
    public int userID;
    public String userName;
    public String userPassword;

    /**used to create user
     *
     */
    public User(){
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**used to get user ID
     *
     * @return user ID
     */
    public int getUserID() {
        return userID;
    }

    /**used to set user ID
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**used to get user name
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**used to set user name
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**used to get user password
     *
     * @return user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**used to set user password
     *
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
