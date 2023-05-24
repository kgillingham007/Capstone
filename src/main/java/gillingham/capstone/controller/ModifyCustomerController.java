package gillingham.capstone.controller;

import gillingham.capstone.Database.JDBC;
import gillingham.capstone.Database.countryDAO;
import gillingham.capstone.model.Country;
import gillingham.capstone.model.Customer;
import gillingham.capstone.Database.firstleveldivisionDAO;
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

/**Controller class for the modify customer screen
 *
 */
public class ModifyCustomerController {

    /**Labels for the modify customer screen
     *
     */
    @FXML
    private TextField modifyCustomerIDTextField;
    @FXML
    private TextField modifyCustomerNameTextField;
    @FXML
    private TextField modifyCustomerAddressTextField;
    @FXML
    private TextField modifyCustomerPhoneNumberTextField;
    @FXML
    private ComboBox modifyCustomerCountryComboBox;
    @FXML
    private ComboBox modifyCustomerStateProvinceComboBox;
    @FXML
    private TextField modifyCustomerPostalCodeTextField;
    @FXML
    private Button modifyCustomerSaveButton;
    @FXML
    private Button modifyCustomerCancelButton;
    private int selectedCountryID;
    private Country selectedCountry;
    private Object selectedDivision;

    /**Used to initialize the data of selectedCustomer from the main screen
     *
     * @param selectedCustomer selected from the customer table in the main screen
     * @throws SQLException if there is an issue accessing the selectedCustomer from the database
     * @throws IOException if there is an issue opening the modify customer screen
     */
    public void initData(Customer selectedCustomer) throws SQLException, IOException {
        try {
            JDBC.openConnection();

            /**Retrieve all countries and set them as the items of the country combo box
             *
             */
            ObservableList<countryDAO> allCountries = countryDAO.getCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();
            allCountries.stream().map(Country :: getCountryName).forEach(countryNames::add);
            modifyCustomerCountryComboBox.setItems(countryNames);

            /**Retrieve all first-level divisions and set them as the items of the division combo box
             *
             */
            ObservableList<firstleveldivisionDAO> allDivisions = firstleveldivisionDAO.getAllFirstLevelDivisions();
            ObservableList<String> divisionNames = FXCollections.observableArrayList();
            allDivisions.stream().map(firstLevelDivision::getDivisionName).forEach(divisionNames::add);
            modifyCustomerStateProvinceComboBox.setItems(divisionNames);

            /**Retrieve the customer's country and division IDs
             *
             */
            int countryID = selectedCustomer.getCustomerCountryID();
            int divisionID = selectedCustomer.getCustomerDivisionID();

            /**Retrieve the country and division objects from their respective DAOs
             *
             */
            Country selectedCountry = countryDAO.getCountryFromCountryID(countryID,divisionID);
            firstLevelDivision selectedDivision = firstleveldivisionDAO.getDivisionFromDivisionID(divisionID);

            /**Set the values of the country and division combo boxes
             *
             */
            modifyCustomerCountryComboBox.setValue(selectedCountry.getCountryName());
            modifyCustomerStateProvinceComboBox.setValue(selectedDivision.getDivisionName());

            /**Set the remaining fields of the modify customer form
             *
             */

            modifyCustomerIDTextField.setText(Integer.toString(selectedCustomer.getCustomerID()));
            modifyCustomerNameTextField.setText(selectedCustomer.getCustomerName());
            modifyCustomerAddressTextField.setText(selectedCustomer.getCustomerAddress());
            modifyCustomerPhoneNumberTextField.setText(selectedCustomer.getCustomerPhoneNumber());
            modifyCustomerPostalCodeTextField.setText(selectedCustomer.getCustomerPostalCode());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**Used to set the firslLeveldivision combo boxes values to the appropriate country selected
     *
     * @param event when the combo box is selected
     * @throws SQLException if there is an issue accessing the database
     * @throws IOException if there is an issue clicking the combo box
     */
    @FXML
    public void modifyCustomerCountryComboBoxClicked(ActionEvent event) throws
            SQLException, IOException {
        try {
            JDBC.openConnection();
            String selectedCountry = (String) modifyCustomerCountryComboBox.getSelectionModel().getSelectedItem();
            ObservableList<firstleveldivisionDAO> getAllFirstLevelDivisions = firstleveldivisionDAO.getAllFirstLevelDivisions();
            ObservableList<String> firstLevelDivisionsUS = FXCollections.observableArrayList();
            ObservableList<String> firstLevelDivisionsUK = FXCollections.observableArrayList();
            ObservableList<String> firstLevelDivisionsCanada = FXCollections.observableArrayList();

            getAllFirstLevelDivisions.forEach(firstLevelDivision -> {
                if (firstLevelDivision.getCountry_ID() == 1) {
                    firstLevelDivisionsUS.add(firstLevelDivision.getDivisionName());
                } else if (firstLevelDivision.getCountry_ID() == 2) {
                    firstLevelDivisionsUK.add(firstLevelDivision.getDivisionName());
                } else if (firstLevelDivision.getCountry_ID() == 3) {
                    firstLevelDivisionsCanada.add(firstLevelDivision.getDivisionName());
                }
            });

            if (selectedCountry.equals("U.S")) {
                modifyCustomerStateProvinceComboBox.setItems(firstLevelDivisionsUS);
                System.out.println("US divisions loaded");
            } else if (selectedCountry.equals("UK")) {
                modifyCustomerStateProvinceComboBox.setItems(firstLevelDivisionsUK);
                System.out.println("UK divisions loaded");
            } else if (selectedCountry.equals("Canada")) {
                modifyCustomerStateProvinceComboBox.setItems(firstLevelDivisionsCanada);
                System.out.println("Canada divisions loaded");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**Used to save the modified customer data to the database
     *
     * @param event when the button is pressed
     * @throws IOException if there is an issue with the button
     */
    @FXML
    void modifyCustomerSaveButtonClicked(ActionEvent event) throws IOException {
        try {
            JDBC.openConnection();
            if (!modifyCustomerNameTextField.getText().isEmpty() ||
                    !modifyCustomerAddressTextField.getText().isEmpty() ||
                    !modifyCustomerPhoneNumberTextField.getText().isEmpty() ||
                    !modifyCustomerCountryComboBox.getSelectionModel().isEmpty() ||
                    !modifyCustomerStateProvinceComboBox.getSelectionModel().isEmpty() ||
                    !modifyCustomerPostalCodeTextField.getText().isEmpty()) {

                int firstLevelDivisionName = 0;
                for (firstleveldivisionDAO firstLevelDivision : firstleveldivisionDAO.getAllFirstLevelDivisions()) {
                    if (modifyCustomerStateProvinceComboBox.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivisionName())) {
                        firstLevelDivisionName = firstLevelDivision.getDivision_ID();
                    }
                }
                String insertStatement = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
                JDBC.setPreparedStatement(JDBC.getConnection(), insertStatement);
                PreparedStatement preparedStatement = JDBC.getPreparedStatement();
                preparedStatement.setInt(1, Integer.parseInt(modifyCustomerIDTextField.getText()));
                preparedStatement.setString(2, modifyCustomerNameTextField.getText());
                preparedStatement.setString(3, modifyCustomerAddressTextField.getText());
                preparedStatement.setString(4, modifyCustomerPostalCodeTextField.getText());
                preparedStatement.setString(5, modifyCustomerPhoneNumberTextField.getText());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(7, "admin");
                preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(9, "admin");
                preparedStatement.setInt(10, firstLevelDivisionName);
                preparedStatement.setInt(11, Integer.parseInt(modifyCustomerIDTextField.getText()));
                preparedStatement.execute();
                System.out.println("Modify customer save button executed");
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**Used to send the application back to the main screen
     *
     * @param event when the button is pressed
     * @throws IOException if there is an issue accessed the main screen
     */
    @FXML
    void modifyCustomerCancelButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }


}