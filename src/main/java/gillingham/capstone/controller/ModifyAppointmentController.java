package gillingham.capstone.controller;

import gillingham.capstone.Database.*;
import gillingham.capstone.model.Appointments;
import gillingham.capstone.model.Contacts;
import gillingham.capstone.model.Customer;
import gillingham.capstone.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

import static gillingham.capstone.model.TimeUtility.timeUTCConverter;

/**Controller class for the modify appointments
 *
 */
public class ModifyAppointmentController {

    /**Labels for the screen
     *
     */
    @FXML
    private TextField modifyAppointmentIDTextField;
    @FXML
    private TextField modifyAppointmentTitleTextField;
    @FXML
    private TextField modifyAppointmentTypeTextField;
    @FXML
    private TextField modifyAppointmentDescriptionTextField;
    @FXML
    private TextField modifyAppointmentLocationTextField;
    @FXML
    private DatePicker modifyAppointmentStartDateDatePicker;
    @FXML
    private DatePicker modifyAppointmentEndDateDatePicker;
    @FXML
    private ComboBox<String> modifyAppointmentStartTimeComboBox;
    @FXML
    private ComboBox<String> modifyAppointmentEndTimeComboBox;
    @FXML
    private TextField modifyAppointmentCustomerIDTextField;
    @FXML
    private TextField modifyAppointmentUserIDTextField;
    @FXML
    private ComboBox<String> modifyAppointmentContactComboBox;
    @FXML private Button modifyAppointmentSaveButton;
    @FXML private Button modifyAppointmentCancelButton;


    /**Used to initialize the data from the selected appointment from the Main Screen
     *
     * @param selectedAppointment selected from the main screen
     * @throws SQLException if there is an issue accessing database or issue with selectedAppointment
     */
    public void initData(Appointments selectedAppointment) throws SQLException {
        JDBC.openConnection();
        ObservableList<Contacts> contactsObservableList = contactDAO.getAllContacts();
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        String displayContactName = "";

        contactsObservableList.forEach(contacts -> allContactNames.add(contacts.getContactName()));
        modifyAppointmentContactComboBox.setItems(allContactNames);
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime startLocal = selectedAppointment.getAppointmentStart().atZone(ZoneOffset.UTC).withZoneSameInstant(localZone);
        ZonedDateTime endLocal = selectedAppointment.getAppointmentEnd().atZone(ZoneOffset.UTC).withZoneSameInstant(localZone);


        for (Contacts contacts : contactsObservableList) {
            if (selectedAppointment.getContactID() == contacts.getContactID()) {
                displayContactName = contacts.getContactName();
            }
        }

        modifyAppointmentIDTextField.setText(Integer.toString(selectedAppointment.getAppointmentID()));
        modifyAppointmentTitleTextField.setText(selectedAppointment.getAppointmentTitle());
        modifyAppointmentTypeTextField.setText(selectedAppointment.getAppointmentType());
        modifyAppointmentDescriptionTextField.setText(selectedAppointment.getAppointmentDescription());
        modifyAppointmentLocationTextField.setText(selectedAppointment.getAppointmentLocation());
        //modifyAppointmentStartDateDatePicker.setValue(selectedAppointment.getAppointmentStart().toLocalDate());
        //modifyAppointmentEndDateDatePicker.setValue(selectedAppointment.getAppointmentEnd().toLocalDate());

        modifyAppointmentStartDateDatePicker.setValue(selectedAppointment.getAppointmentStart().atZone(ZoneOffset.UTC).withZoneSameInstant(localZone).toLocalDate());
        modifyAppointmentEndDateDatePicker.setValue(selectedAppointment.getAppointmentEnd().atZone(ZoneOffset.UTC).withZoneSameInstant(localZone).toLocalDate());


        //modifyAppointmentStartTimeComboBox.setValue(String.valueOf(selectedAppointment.getAppointmentStart().toLocalTime()));
        //modifyAppointmentEndTimeComboBox.setValue(String.valueOf(selectedAppointment.getAppointmentEnd().toLocalTime()));


        modifyAppointmentStartTimeComboBox.setValue(String.valueOf(startLocal.toLocalTime()));
        modifyAppointmentEndTimeComboBox.setValue(String.valueOf(endLocal.toLocalTime()));
        modifyAppointmentCustomerIDTextField.setText(String.valueOf(selectedAppointment.getCustomerID()));
        modifyAppointmentUserIDTextField.setText(String.valueOf(selectedAppointment.getUserID()));
        modifyAppointmentContactComboBox.setValue(displayContactName);

        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);
        if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
            while (firstAppointment.isBefore(lastAppointment)) {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);
            }
        }
        modifyAppointmentStartTimeComboBox.setItems(appointmentTimes);
        modifyAppointmentEndTimeComboBox.setItems(appointmentTimes);
    }

    /**Used to save the modified appointment to the database
     *
     * @param event when the save button is clicked
     * @throws SQLException if there is an issue saving the appointment to the database
     */
    @FXML void modifyAppointmentSaveButtonClicked(ActionEvent event) throws SQLException{
        try {
            Connection connection = JDBC.openConnection();

            if (!modifyAppointmentTitleTextField.getText().isEmpty() && !modifyAppointmentTypeTextField.getText().isEmpty() && !modifyAppointmentDescriptionTextField.getText().isEmpty() &&
                    !modifyAppointmentLocationTextField.getText().isEmpty() && modifyAppointmentStartDateDatePicker.getValue() != null && modifyAppointmentEndDateDatePicker.getValue() != null &&
                    !modifyAppointmentStartTimeComboBox.getValue().isEmpty() && !modifyAppointmentEndTimeComboBox.getValue().isEmpty() && !modifyAppointmentCustomerIDTextField.getText().isEmpty() &&
                    !modifyAppointmentUserIDTextField.getText().isEmpty() && !modifyAppointmentContactComboBox.getValue().isEmpty()){

                ObservableList<Customer> getAllCustomers = customerDAO.getAllCustomers();
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<userDAO> getAllUserIDs = userDAO.getAllUsers();
                ObservableList<Integer> storeUserIDs = getAllUserIDs.stream().map(User::getUserID).collect(Collectors.toCollection(FXCollections::observableArrayList));
                //ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointments> getAllAppointments = appointmentDAO.getAllAppointments();

                getAllCustomers.stream().map(Customer::getCustomerID).forEach(storeCustomerIDs::add);
                getAllUserIDs.stream().map(User::getUserID).forEach(storeUserIDs::add);

                LocalDate localDateStart = modifyAppointmentStartDateDatePicker.getValue();
                LocalDate localDateEnd = modifyAppointmentEndDateDatePicker.getValue();

                DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");
                String appointmentStartDate = modifyAppointmentStartDateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentStartTime = modifyAppointmentStartTimeComboBox.getValue();

                String appointmentEndDate = modifyAppointmentEndDateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentEndTime = modifyAppointmentEndTimeComboBox.getValue();

                System.out.println("This Date + This Start " + appointmentStartDate + " " + appointmentStartTime + ":00");
                String startUTC = timeUTCConverter(appointmentStartDate + " " + appointmentStartTime + ":00");
                String endUTC = timeUTCConverter(appointmentEndDate + " " + appointmentEndTime + ":00");

                LocalTime localTimeStart = LocalTime.parse(modifyAppointmentStartTimeComboBox.getValue(),minHourFormat);
                LocalTime localTimeEnd = LocalTime.parse(modifyAppointmentEndTimeComboBox.getValue(),minHourFormat);

                LocalDateTime localDateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
                LocalDateTime localDateTimeEnd = LocalDateTime.of(localDateEnd , localTimeEnd);

                ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(localDateTimeStart, ZoneId.systemDefault());
                ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(localDateTimeEnd , ZoneId.systemDefault());

                ZonedDateTime convertESTStart =zonedDateTimeStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime convertESTEnd =zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                LocalTime appointmentStartTimeToCheck = convertESTStart.toLocalTime();
                LocalTime appointmentEndTimeToCheck = convertESTEnd.toLocalTime();

                DayOfWeek appointmentStartDayToCheck = convertESTStart.toLocalDate().getDayOfWeek();
                DayOfWeek appointmentEndDayToCheck = convertESTEnd.toLocalDate().getDayOfWeek();

                int appointmentStartDayIntToCheck = appointmentStartDayToCheck.getValue();
                int appointmentEndDayIntToCheck = appointmentEndDayToCheck.getValue();

                int startWorkWeek = DayOfWeek.MONDAY.getValue();
                int endWorkWeek = DayOfWeek.FRIDAY.getValue();

                LocalTime businessStartEST = LocalTime.of(8,0,0);
                LocalTime businessEndEST = LocalTime.of(22,0,0);

                /*Check if the days are outside business hours
                 *
                 */
                if (appointmentStartDayIntToCheck < startWorkWeek || appointmentStartDayIntToCheck > endWorkWeek ||
                        appointmentEndDayIntToCheck < startWorkWeek || appointmentEndDayIntToCheck > endWorkWeek){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Day selected is outside normal business days. Normal business days are Monday - Friday.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Day is outside normal business days");
                    return;
                }

                /*Check if the days are outside business hours
                 *
                 */
                if (appointmentStartTimeToCheck.isBefore(businessStartEST) || appointmentStartTimeToCheck.isAfter(businessEndEST)||
                        appointmentEndTimeToCheck.isBefore(businessStartEST) || appointmentEndTimeToCheck.isAfter(businessEndEST)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Time selected is outside normal business hours. Normal business hours is 8:00AM - 10:00PM EST.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Time is outside normal business hours.");
                    return;
                }

                int modifyAppointmentID = Integer.parseInt(modifyAppointmentIDTextField.getText());
                int customerID = Integer.parseInt(modifyAppointmentCustomerIDTextField.getText());

                /*Used to check the start time compared to the end time
                 *
                 */
                if (localDateTimeStart.isAfter(localDateTimeEnd)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has start time after end time.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Appointment start time is after end time");
                    return;
                }

                /*Used to check the start time compared to the end time
                 *
                 */
                if (localDateTimeStart.isEqual(localDateTimeEnd)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has same start and end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Appointment has same start and end time");
                    return;
                }

                for (Appointments appointments: getAllAppointments){
                    LocalDateTime appointmentStartCheck = appointments.getAppointmentStart();
                    LocalDateTime appointmentEndCheck = appointments.getAppointmentEnd();

                    /*Used to check if there are overlapping appointments
                     *
                     */
                    if ((customerID == appointments.getCustomerID()) && (modifyAppointmentID != appointments.getAppointmentID()) && (localDateTimeStart.isBefore(appointmentStartCheck))
                            && (localDateTimeEnd.isAfter(appointmentEndCheck))){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps existing appointment");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment overlaps an existing appointment");
                        return;
                    }

                    /*Used to check if there are overlapping appointments
                     *
                     */
                    if ((customerID == appointments.getCustomerID()) && (modifyAppointmentID != appointments.getAppointmentID())
                            && (localDateTimeStart.isAfter(appointmentStartCheck)) && (localDateTimeStart.isBefore(appointmentEndCheck))){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment start time overlaps an existing appointment");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment start time overlaps an existing appointment");
                        return;
                    }

                    /*Used to check if there are overlapping appointments
                     *
                     */
                    if (customerID == appointments.getCustomerID() && (modifyAppointmentID != appointments.getAppointmentID())
                            && (localDateTimeEnd.isAfter(appointmentStartCheck)) && (localDateTimeEnd.isBefore(appointmentEndCheck))){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment end time overlaps an existing appointment");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment end time overlaps an existing appointment");
                        return;
                    }
                }

                String insertStatementSQL = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

                JDBC.setPreparedStatement(JDBC.getConnection(), insertStatementSQL);
                PreparedStatement preparedStatement = JDBC.getPreparedStatement();
                //preparedStatement.setInt(1,Integer.parseInt(modifyAppointmentIDTextField.getText()));
                preparedStatement.setString(1,modifyAppointmentTitleTextField.getText());
                preparedStatement.setString(2,modifyAppointmentDescriptionTextField.getText());
                preparedStatement.setString(3,modifyAppointmentLocationTextField.getText());
                preparedStatement.setString(4,modifyAppointmentTypeTextField.getText());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(startUTC));
                preparedStatement.setTimestamp(6, Timestamp.valueOf(endUTC));
                preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(8, "admin");
                preparedStatement.setInt(9, Integer.parseInt(modifyAppointmentCustomerIDTextField.getText()) );
                preparedStatement.setInt(10, Integer.parseInt(modifyAppointmentUserIDTextField.getText()));
                preparedStatement.setInt(11, Integer.parseInt(contactDAO.findContactID(modifyAppointmentContactComboBox.getValue())));

                //preparedStatement.setInt(10,Integer.parseInt(contactDAO.findContactID(modifyAppointmentUserIDTextField.getText())));
                //preparedStatement.setInt(12, Integer.parseInt(contactDAO.findContactID(modifyAppointmentContactComboBox.getValue())));

                preparedStatement.setInt(12, Integer.parseInt(modifyAppointmentIDTextField.getText()));

                preparedStatement.execute();
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
        }
        catch (SQLException | IOException throwable){
            throwable.printStackTrace();
        }
    }


    /**Used to go back to the main screen when the cancel button is pressed
     *
     * @param event when the button is pressed
     * @throws IOException if there is an issue accessing the main screen
     */
    @FXML void modifyAppointmentCancelButtonClicked (ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}