package gillingham.capstone.controller;

import gillingham.capstone.Database.JDBC;
import gillingham.capstone.Database.appointmentDAO;
import gillingham.capstone.Database.customerDAO;
import gillingham.capstone.model.Appointments;
import gillingham.capstone.model.Customer;
import gillingham.capstone.model.Search;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static gillingham.capstone.Database.JDBC.connection;

/**Controller for the main screen
 *
 */
public class MainScreenController {

    Stage stage;

    /**Labels for the main screen
     *
     */

    //******** Appointment Related: ********
    @FXML
    private RadioButton mainScreenCurrentWeekRadioButton;
    @FXML private RadioButton mainScreenCurrentMonthRadioButton;
    @FXML private RadioButton mainScreenAllAppointmentsRadioButton;
    @FXML private TableView<Appointments> mainScreenAppointmentsTableView;
    @FXML private TableColumn<?,?> mainScreenAppointmentIDColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentTitleColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentTypeColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentDescriptionColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentLocationColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentStartDateColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentEndDateColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentCustomerIDColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentUserIDColumn;
    @FXML private TableColumn<?,?> mainScreenAppointmentContactIDColumn;
    @FXML private Button mainScreenAddAppointmentButton;
    @FXML private Button mainScreenModifyAppointmentButton;
    @FXML private Button mainScreenDeleteAppointmentButton;


    //******** Customer Related: ********
    @FXML private TableView<Customer> mainScreenCustomerTableView;
    @FXML private TableColumn<?,?> mainScreenCustomerIDColumn;
    @FXML private TableColumn<?,?> mainScreenCustomerNameColumn;
    @FXML private TableColumn<?,?> mainScreenCustomerAddressColumn;
    @FXML private TableColumn<?,?> mainScreenCustomerPhoneColumn;
    @FXML private TableColumn<?,?> mainScreenCustomerStateColumn;
    @FXML private TableColumn<?,?> mainScreenCustomerPostalColumn;
    @FXML private Button mainScreenAddCustomerButton;
    @FXML private Button mainScreenModifyCustomerButton;
    @FXML private Button mainScreenDeleteCustomerButton;


    //******** Reports Related: ********\
    @FXML private Button mainScreenReportsButton;
    @FXML public Button mainScreenExitButton;
    @FXML private TextField mainScreenCustomersSearchField;
    @FXML private TextField mainScreenAppointmentsSearchField;


    /**Initialize the main screen
     *
     * @throws SQLException if there was an issue accessing the database
     */
    public void initialize() throws SQLException{
        JDBC.openConnection();
        //******** Appointment Related: ********
        ObservableList<Appointments> allAppointmentsList = appointmentDAO.getAllAppointments();
        mainScreenAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        mainScreenAppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        mainScreenAppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        mainScreenAppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        mainScreenAppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        mainScreenAppointmentStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        mainScreenAppointmentEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        mainScreenAppointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        mainScreenAppointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        mainScreenAppointmentContactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));


        mainScreenAppointmentsTableView.setItems(allAppointmentsList);


        //******** Customer Related: ********
        ObservableList<Customer> allCustomersList = customerDAO.getAllCustomers(connection);
        mainScreenCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        mainScreenCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        mainScreenCustomerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        mainScreenCustomerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        mainScreenCustomerStateColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        mainScreenCustomerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));

        mainScreenCustomerTableView.setItems(allCustomersList);
    }

    //******** Appointment Related: ********

    /**Used to open the add appointment screen
     *
     * @param event when the add appointment button is clicked
     * @throws IOException if there is an issue opening the add appointment screen
     */
    @FXML void mainScreenAddAppointmentButtonClicked(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gillingham/capstone/addappointment.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

    /**Used to open the modify appointment screen
     *
     * @param event when the modify appointment button is clicked
     * @throws IOException if there is an issue opening the modify appointment screen
     * @throws SQLException if there is an issue accessing the database
     */
    @FXML void mainScreenModifyAppointmentButtonClicked(ActionEvent event) throws IOException, SQLException {
        Appointments selectedAppointment = mainScreenAppointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gillingham/capstone/modifyappointments.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            ModifyAppointmentController controller = loader.getController();
            controller.initData(selectedAppointment);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No appointment selected");
            alert.show();
        }
    }

    /**Used to delete selected appointment
     *
     * @param event when there is an appointment selected and the delete button is clicked
     * @throws Exception if there is no appointment selected and delete button is attempted
     */
    @FXML void mainScreenDeleteAppointmentButtonClicked(ActionEvent event) throws Exception{
        try{
            Connection connection = JDBC.openConnection();
            int deleteAppointmentID = mainScreenAppointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String deleteAppointmentType = mainScreenAppointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected Appointment with Appointment ID: " + deleteAppointmentID + " and Appointment Type: " + deleteAppointmentType);
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK){
                appointmentDAO.deleteAppointment(deleteAppointmentID,connection);
                ObservableList<Appointments> allAppointmentsList = appointmentDAO.getAllAppointments();
                mainScreenAppointmentsTableView.setItems(allAppointmentsList);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**Used to display all appointments in the appointment table
     *
     * @param event when the radio button is pressed
     * @throws SQLException if there is an issue accessing the database
     */
    @FXML void mainScreenAllAppointmentsRadioButtonClicked(ActionEvent event) throws SQLException{
        try {
            ObservableList<Appointments> allAppointmentsList = appointmentDAO.getAllAppointments();
            if (allAppointmentsList != null)
                for (gillingham.capstone.model.Appointments appointments : allAppointmentsList){
                    mainScreenAppointmentsTableView.setItems(allAppointmentsList);
                }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**Used to filter the appointments table to only show current month appointments
     *
     * @param event when the radio button is pressed
     * @throws SQLException if there is an issue accessing the database
     * There is a lambda expression in this function Line:220
     */
    @FXML void mainScreenCurrentMonthRadioButtonClicked(ActionEvent event) throws SQLException{
        try{
            ObservableList<Appointments> allAppointmentsList = appointmentDAO.getAllAppointments();
            ObservableList<Appointments> allAppointmentsMonth = FXCollections.observableArrayList();

            LocalDateTime currentMonthStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);

            if (allAppointmentsList != null)
            /**Lambda expression that parses the data and only leaves appointments that match current month and then sets those items to the tableview
             *
             */
                allAppointmentsList.forEach(appointments -> {
                    if (appointments.getAppointmentEnd().isAfter(currentMonthStart) && appointments.getAppointmentEnd().isBefore(currentMonthEnd)){
                        allAppointmentsMonth.add(appointments);
                    }
                    mainScreenAppointmentsTableView.setItems(allAppointmentsMonth);
                });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**Used to filter the appointment table to only show current week appointments
     *
     * @param event when the radio button is pressed
     * @throws SQLException if there is an issue accessing the database
     * there is a lambda expression in this function Line:242
     */
    @FXML void mainScreenCurrentWeekRadioButtonClicked(ActionEvent event) throws SQLException{
        try {
            ObservableList<Appointments> allAppointmentsList = appointmentDAO.getAllAppointments();
            ObservableList<Appointments> allAppointmentsWeek = FXCollections.observableArrayList();

            LocalDateTime currentWeekStart = LocalDateTime.now().minusWeeks(1);
            LocalDateTime currentWeekEnd = LocalDateTime.now().plusWeeks(1);

            /**
             * Lambda expression that parses the data and only leaves appointments that match current week and then sets those items to the tableview
             */
            if (allAppointmentsList != null) allAppointmentsList.forEach(appointments -> {
                if (appointments.getAppointmentEnd().isAfter(currentWeekStart) && appointments.getAppointmentEnd().isBefore(currentWeekEnd)){
                    allAppointmentsWeek.add(appointments);
                }
                mainScreenAppointmentsTableView.setItems(allAppointmentsWeek);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**Used for the Appointment related search field
     *
     * @param event when the search field is typed into or entered
     */
    @FXML public void mainScreenAppointmentsSearchFieldEntered(ActionEvent event){
        String text = mainScreenAppointmentsSearchField.getText();
        ObservableList<Appointments> appointments = Search.lookUpAppointment_Name(text);
        if (text.matches("")){
            mainScreenAppointmentsTableView.setItems(Appointments.getAllAppointments());
            mainScreenAppointmentsSearchField.setPromptText("Search by Appointment Title");
        }
        if (Search.lookUpAppointment_Name(text).size() == 0){
            mainScreenAppointmentsSearchField.setText("");
            mainScreenAppointmentsSearchField.setPromptText("Part does not exist");
        }
        else mainScreenAppointmentsTableView.setItems(appointments);
    }





    //******** Customer Related: ********


    /**Used to open the add customer screen
     *
     * @param event when the add customer button is pressed
     * @throws SQLException if there is an issue accessing the database
     * @throws IOException if there is an issue opening the add customer
     */
    @FXML void mainScreenAddCustomerButtonClicked (ActionEvent event) throws SQLException, IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/gillingham/capstone/addcustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**Used to open the modify customer screen
     *
     * @param event when the modify customer button is pressed
     * @throws SQLException if there is an issue accessing the database
     * @throws IOException if there is an issue opening the modify customer
     */
    @FXML void mainScreenModifyCustomerButtonClicked(ActionEvent event) throws SQLException, IOException {
        Customer selectedCustomer = mainScreenCustomerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gillingham/capstone/modifycustomers.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            ModifyCustomerController controller = loader.getController();
            controller.initData(selectedCustomer);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No appointment selected");
            alert.show();
        }
    }

    /** Used to delete the selected customer
     *
     * @param event when there is a selected customer and the delete button is pressed
     * @throws SQLException if there is an issue accessing the database to delete the selected customer
     */
    @FXML void mainScreenDeleteCustomerButtonClicked (ActionEvent event) throws SQLException{
        Connection connection = JDBC.openConnection();
        ObservableList<Appointments> allAppointmentsList = appointmentDAO.getAllAppointments();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected customer and all associated appointments?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK){
            int deleteCustomerID = mainScreenCustomerTableView.getSelectionModel().getSelectedItem().getCustomerID();
            appointmentDAO.deleteAppointment(deleteCustomerID,connection);
            String sqlDelete = "DELETE FROM customers WHERE Customer_ID = ?";
            JDBC.setPreparedStatement(JDBC.getConnection(),sqlDelete);
            PreparedStatement preparedStatementDelete = JDBC.getPreparedStatement();
            int customerIDFromTable = mainScreenCustomerTableView.getSelectionModel().getSelectedItem().getCustomerID();

            for (Appointments appointments : allAppointmentsList){
                int customerFromAppointments = appointments.getCustomerID();
                if (customerIDFromTable == customerFromAppointments){
                    String selectedAppointmentsDelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
                    JDBC.setPreparedStatement(JDBC.getConnection(),selectedAppointmentsDelete);
                }
            }
            preparedStatementDelete.setInt(1,customerIDFromTable);
            preparedStatementDelete.execute();
            ObservableList<Customer> updatedCustomerList = customerDAO.getAllCustomers(connection);
            mainScreenCustomerTableView.setItems(updatedCustomerList);
            ObservableList<Appointments> updatedAppointmentsList = appointmentDAO.getAllAppointments();
            mainScreenAppointmentsTableView.setItems(updatedAppointmentsList);
        }
    }

    /**Used for the Customer related search field
     *
     * @param event when the search field is typed into or entered
     */
    @FXML public void mainScreenCustomersSearchFieldEntered(ActionEvent event){
        String text = mainScreenCustomersSearchField.getText();
        ObservableList<Customer> customers = Search.lookUpCustomer_Name(text);
        if (text.matches("")){
            mainScreenCustomerTableView.setItems(Customer.getAllCustomers());
            mainScreenCustomersSearchField.setPromptText("Search by Customer Name");
        }
        if (Search.lookUpCustomer_Name(text).size() == 0){
            mainScreenCustomersSearchField.setText("");
            mainScreenCustomersSearchField.setPromptText("Customer does not exist");
        }
        else mainScreenCustomerTableView.setItems(customers);
    }


    //******** Reports Related: ********


    /**Used to open the reports screen
     *
     * @param event when the button is pressed
     * @throws SQLException if there is an issue accessing the database
     * @throws IOException if there is an issue loading the reports screen
     */
    @FXML void mainScreenReportsButtonClicked (ActionEvent event) throws SQLException, IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/gillingham/capstone/reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    //******** MISC: ********


    /**Used to exit the entire program
     *
     * @param event when the exit button is pressed
     * @throws IOException if there is an issue exiting the program
     */
    @FXML void mainScreenExitButtonClicked(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(((Node) event.getSource()).getScene().getWindow());
        alert.setHeaderText("Are you sure you want to Exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            System.exit(0);
        }
    }

}