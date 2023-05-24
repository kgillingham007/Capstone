package gillingham.capstone.model;

import gillingham.capstone.Database.JDBC;
import gillingham.capstone.Database.countryDAO;
import gillingham.capstone.Database.firstleveldivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Customer class model
 *
 */
public class Customer {
    /**Customer identifiers
     *
     */

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private String divisionName;
    private int divisionID;
    private ObservableList<Country> customerCountry = FXCollections.observableArrayList();
    private ObservableList<firstLevelDivision> customerFLDivision = FXCollections.observableArrayList();
    private int customerCountryID;

    /**Used to get customerCountryID
     *
     * @return customerCountryID
     */
    public int getCustomerCountryID() {
        return customerCountryID;
    }

    /**Used to set customerCountryID
     *
     * @param customerCountryID
     */
    public void setCustomerCountryID(int customerCountryID) {
        this.customerCountryID = customerCountryID;
    }

    /**Used to create customer
     *
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhoneNumber
     * @param divisionName
     * @param divisionID
     */
    public Customer(int customerID , String customerName , String customerAddress
            , String customerPostalCode , String customerPhoneNumber , String divisionName
            , int divisionID){
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionName = divisionName;
        this.divisionID = divisionID;
    }

    /**Used to get customer ID
     *
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**Used to set customer ID
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**Used to get customer name
     *
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**used to set customer name
     *
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**used to get customer address
     *
     * @return customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**used to set customer address
     *
     * @param customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**used to get customer postal code
     *
     * @return customer postal code
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**used to set customer postal code
     *
     * @param customerPostalCode
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**used to get customer phone
     *
     * @return customer phone
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**used to set customer phone
     *
     * @param customerPhoneNumber
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**used to get division name
     *
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**used to set division name
     *
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**used to get division ID
     *
     * @return division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**used to set division ID
     *
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**used to get customerDivisionID
     *
     * @return customerDivisionID
     */
    public int getCustomerDivisionID() {
        return divisionID;
    }


}
