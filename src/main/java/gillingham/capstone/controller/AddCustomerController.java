package gillingham.capstone.controller;

import gillingham.capstone.Database.JDBC;
import gillingham.capstone.Database.countryDAO;
import gillingham.capstone.Database.customerDAO;
import gillingham.capstone.Database.firstleveldivisionDAO;
import gillingham.capstone.model.Country;
import gillingham.capstone.model.Customer;
import gillingham.capstone.model.firstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

/**Controller for the add customer Screen
 *
 */
public class AddCustomerController {

    /**Labels for all items on the add customer screen
     *
     */
    @FXML
    private TextField addCustomerIDTextField;
    @FXML private TextField addCustomerNameTextField;
    @FXML private TextField addCustomerAddressTextField;
    @FXML private TextField addCustomerPhoneNumberTextField;
    @FXML private ComboBox addCustomerCountryComboBox;
    @FXML private ComboBox addCustomerStateProvinceComboBox;
    @FXML private TextField addCustomerPostalCodeTextField;
    @FXML private Button addCustomerSaveButton;
    @FXML private Button addCustomerCancelButton;

    /**Initialize function for add appointment controller
     * @throws SQLException if the initialize doesnt initialize correctly
     * There is a lambda expression within this Function Line:67
     */
    public void initialize(){
        try {
            JDBC.openConnection();

            ObservableList<countryDAO> allCountries = countryDAO.getCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();
            ObservableList<firstleveldivisionDAO> allFirstLevelDivisions = firstleveldivisionDAO.getAllFirstLevelDivisions();
            ObservableList<String> firstLevelDivisionAllNames = FXCollections.observableArrayList();
            //ObservableList<Customer> allCustomersList = customerDAO.getAllCustomers(connection);

            allCountries.stream().map(Country::getCountryName).forEach(countryNames::add);
            addCustomerCountryComboBox.setItems(countryNames);

            /**Lambda expression that gets all firstLevelDivisions and adds them to the firstLevelDivision variable
             *
             */
            //allFirstLevelDivisions.forEach(firstleveldivisionDAO -> firstLevelDivisionAllNames.add(firstLevelDivision.getDivisionName()));
            allFirstLevelDivisions.stream().map(firstleveldivisionDAO::getDivisionName).forEach(firstLevelDivisionAllNames::add);

            addCustomerStateProvinceComboBox.setItems(firstLevelDivisionAllNames);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Function for the add customer Combo box
     * <p>
     *     sets the first level division combo box to the appropriate country selected.
     * </p>
     *
     * @param event when the combo box is clicked
     * @throws SQLException if there is an issue getting the data to load into the combo box
     */
    public void addCustomerCountryComboBoxClicked(ActionEvent event) throws SQLException{
        try {
            JDBC.openConnection();
            String selectedCountry = (String) addCustomerCountryComboBox.getSelectionModel().getSelectedItem();
            ObservableList<firstleveldivisionDAO> getAllFirstLevelDivisions = firstleveldivisionDAO.getAllFirstLevelDivisions();
            ObservableList<String> firstLevelDivisionsUS = FXCollections.observableArrayList();
            ObservableList<String> firstLevelDivisionsUK = FXCollections.observableArrayList();
            ObservableList<String> firstLevelDivisionsCanada = FXCollections.observableArrayList();

            getAllFirstLevelDivisions.forEach(firstLevelDivision -> {
                System.out.println("firstLevelDivision.getCountry_ID(): " + firstLevelDivision.getCountry_ID());
                if (firstLevelDivision.getCountry_ID() == 1){
                    firstLevelDivisionsUS.add(firstLevelDivision.getDivisionName());
                }
                else if (firstLevelDivision.getCountry_ID() == 2){
                    firstLevelDivisionsUK.add(firstLevelDivision.getDivisionName());
                }
                else if (firstLevelDivision.getCountry_ID() == 3){
                    firstLevelDivisionsCanada.add(firstLevelDivision.getDivisionName());
                }
            });
            System.out.println("firstLevelDivisionsUS size: " + firstLevelDivisionsUS.size());
            System.out.println("firstLevelDivisionsUK size: " + firstLevelDivisionsUK.size());
            System.out.println("firstLevelDivisionsCanada size: " + firstLevelDivisionsCanada.size());

            if (selectedCountry.equalsIgnoreCase("U.S")){
                addCustomerStateProvinceComboBox.setItems(firstLevelDivisionsUS);
            }
            else if (selectedCountry.equalsIgnoreCase("UK")){
                addCustomerStateProvinceComboBox.setItems(firstLevelDivisionsUK);
            }
            else if (selectedCountry.equalsIgnoreCase("Canada")){
                addCustomerStateProvinceComboBox.setItems(firstLevelDivisionsCanada);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Function for the add customer save button
     *
     * @param event when the add customer save button is pressed
     * @throws SQLException if there is an error or issue saving the customer data to the database
     */
    @FXML public void addCustomerSaveButtonClicked(ActionEvent event) throws SQLException{
        try {
            JDBC.openConnection();
            if (!addCustomerNameTextField.getText().isEmpty() ||
                    !addCustomerAddressTextField.getText().isEmpty() ||
                    !addCustomerPhoneNumberTextField.getText().isEmpty() ||
                    !addCustomerCountryComboBox.getSelectionModel().isEmpty() ||
                    !addCustomerStateProvinceComboBox.getSelectionModel().isEmpty() ||
                    !addCustomerPostalCodeTextField.getText().isEmpty()) {
                //Integer newCustomerID = (int) (Math.random() *100);
                int firstLevelDivisionName = 0;
                for (firstleveldivisionDAO firstLevelDivision : firstleveldivisionDAO.getAllFirstLevelDivisions()){
                    if (addCustomerStateProvinceComboBox.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivisionName())){
                        firstLevelDivisionName = firstLevelDivision.getDivision_ID();
                    }
                }
                String insertStatement = "INSERT INTO customers ( Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?)";
                JDBC.setPreparedStatement(JDBC.getConnection(),insertStatement);
                PreparedStatement preparedStatement = JDBC.getPreparedStatement();
                //preparedStatement.setInt(1,newCustomerID);
                preparedStatement.setString(1,addCustomerNameTextField.getText());
                preparedStatement.setString(2,addCustomerAddressTextField.getText());
                preparedStatement.setString(3,addCustomerPostalCodeTextField.getText());
                preparedStatement.setString(4,addCustomerPhoneNumberTextField.getText());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(6,"admin");
                preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(8,"admin");
                preparedStatement.setInt(9,firstLevelDivisionName);
                preparedStatement.execute();
            }
            //JDBC.closeConnection();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



    /** Function to take program back to main screen
     *
     * @param event when the cancel button is clicked
     * @throws IOException if the program isnt able to move back to the main screen
     */
    @FXML public void addCustomerCancelButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

}