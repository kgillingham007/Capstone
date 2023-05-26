package gillingham.capstone.controller;

import gillingham.capstone.Database.*;
import gillingham.capstone.model.Appointments;
import gillingham.capstone.model.Contacts;
import gillingham.capstone.model.Customer;
import gillingham.capstone.model.User;

import static gillingham.capstone.model.TimeUtility.timeUTCConverter;

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
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/**
 * Controller for the add appointment Screen
 */
public class AddAppointmentController {

    /**
     * Labels for all items on the add appointment screen
     */
    @FXML
    private TextField addAppointmentIDTextField;
    @FXML
    private TextField addAppointmentTitleTextField;
    @FXML
    private TextField addAppointmentTypeTextField;
    @FXML
    private TextField addAppointmentDescriptionTextField;
    @FXML
    private TextField addAppointmentLocationTextField;
    @FXML
    private DatePicker addAppointmentStartDateDatePicker;
    @FXML
    private DatePicker addAppointmentEndDateDatePicker;
    @FXML
    private ComboBox<String> addAppointmentStartTimeComboBox;
    @FXML
    private ComboBox<String> addAppointmentEndTimeComboBox;
    @FXML
    private TextField addAppointmentCustomerIDTextField;
    @FXML
    private TextField addAppointmentUserIDTextField;
    @FXML
    private ComboBox<String> addAppointmentContactComboBox;
    @FXML
    private Button addAppointmentSaveButton;
    @FXML
    private Button addAppointmentCancelButton;


    /**
     * Initialize function for add appointment controller
     *
     * @throws SQLException if the initialize doesnt initialize correctly
     * <p>
     * There is a Lambda expression within this function line:87
     * </p>
     */
    @FXML
    public void initialize() throws SQLException {
        ObservableList<Contacts> contactsObservableList = contactDAO.getAllContacts();

        ObservableList<String> allContactsNames = FXCollections.observableArrayList();

        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

        /**Lambda Expression for getting the contact names and adding them to contact
         *
         */
        contactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContactName()));
        //userObservableList.forEach(userDAO -> allUserIDs.add(userDAO.getUserID()));


        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

        if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
            while (firstAppointment.isBefore(lastAppointment)) {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);
            }
        }
        addAppointmentStartTimeComboBox.setItems(appointmentTimes);
        addAppointmentEndTimeComboBox.setItems(appointmentTimes);
        addAppointmentContactComboBox.setItems(allContactsNames);
    }


    /**
     * Function for the add appointment save button
     *
     * @param event when the save button is clicked
     * @throws IOException if the program isnt able to save the appointment. Or its unable to return to the main screen after saving
     */
    @FXML
    void addAppointmentSaveButtonClicked(ActionEvent event) throws IOException {
        try {
            Connection connection = JDBC.openConnection();

            if (!addAppointmentTitleTextField.getText().isEmpty() && !addAppointmentTypeTextField.getText().isEmpty() && !addAppointmentDescriptionTextField.getText().isEmpty() &&
                    !addAppointmentLocationTextField.getText().isEmpty() && addAppointmentStartDateDatePicker.getValue() != null && addAppointmentEndDateDatePicker.getValue() != null &&
                    !addAppointmentStartTimeComboBox.getValue().isEmpty() && !addAppointmentEndTimeComboBox.getValue().isEmpty() && !addAppointmentCustomerIDTextField.getText().isEmpty() &&
                    !addAppointmentUserIDTextField.getText().isEmpty() && !addAppointmentContactComboBox.getValue().isEmpty()) {

                ObservableList<Customer> getAllCustomers = customerDAO.getAllCustomers();
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<userDAO> getAllUserIDs = userDAO.getAllUsers();
                //ObservableList<Integer> storeUserIDs = getAllUserIDs.stream().map(User::getUserID).collect(Collectors.toCollection(FXCollections::observableArrayList));
                ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointments> getAllAppointments = appointmentDAO.getAllAppointments();

                getAllCustomers.stream().map(Customer::getCustomerID).forEach(storeCustomerIDs::add);
                getAllUserIDs.stream().map(User::getUserID).forEach(storeUserIDs::add);

                LocalDate localDateStart = addAppointmentStartDateDatePicker.getValue();
                LocalDate localDateEnd = addAppointmentEndDateDatePicker.getValue();

                DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");
                String appointmentStartDate = addAppointmentStartDateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentStartTime = addAppointmentStartTimeComboBox.getValue();

                String appointmentEndDate = addAppointmentEndDateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentEndTime = addAppointmentEndTimeComboBox.getValue();

                System.out.println("This Date + This Start " + appointmentStartDate + " " + appointmentStartTime + ":00");
                String startUTC = timeUTCConverter(appointmentStartDate + " " + appointmentStartTime + ":00");
                String endUTC = timeUTCConverter(appointmentEndDate + " " + appointmentEndTime + ":00");

                LocalTime localTimeStart = LocalTime.parse(addAppointmentStartTimeComboBox.getValue(), minHourFormat);
                LocalTime localTimeEnd = LocalTime.parse(addAppointmentEndTimeComboBox.getValue(), minHourFormat);

                LocalDateTime localDateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
                LocalDateTime localDateTimeEnd = LocalDateTime.of(localDateEnd, localTimeEnd);

                ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(localDateTimeStart, ZoneId.systemDefault());
                ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(localDateTimeEnd, ZoneId.systemDefault());

                ZonedDateTime convertESTStart = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime convertESTEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                LocalTime appointmentStartTimeToCheck = convertESTStart.toLocalTime();
                LocalTime appointmentEndTimeToCheck = convertESTEnd.toLocalTime();

                DayOfWeek appointmentStartDayToCheck = convertESTStart.toLocalDate().getDayOfWeek();
                DayOfWeek appointmentEndDayToCheck = convertESTEnd.toLocalDate().getDayOfWeek();

                int appointmentStartDayIntToCheck = appointmentStartDayToCheck.getValue();
                int appointmentEndDayIntToCheck = appointmentEndDayToCheck.getValue();

                int startWorkWeek = DayOfWeek.MONDAY.getValue();
                int endWorkWeek = DayOfWeek.FRIDAY.getValue();

                LocalTime businessStartEST = LocalTime.of(8, 0, 0);
                LocalTime businessEndEST = LocalTime.of(22, 0, 0);

                if (appointmentStartDayIntToCheck < startWorkWeek || appointmentStartDayIntToCheck > endWorkWeek ||
                        appointmentEndDayIntToCheck < startWorkWeek || appointmentEndDayIntToCheck > endWorkWeek) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Day selected is outside normal business days. Normal business days are Monday - Friday.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Day is outside normal business days");
                    return;
                }

                if (appointmentStartTimeToCheck.isBefore(businessStartEST) || appointmentStartTimeToCheck.isAfter(businessEndEST) ||
                        appointmentEndTimeToCheck.isBefore(businessStartEST) || appointmentEndTimeToCheck.isAfter(businessEndEST)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Time selected is outside normal business hours. Normal business hours is 8:00AM - 10:00PM EST.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Time is outside normal business hours.");
                    return;
                }

                //int newAppointmentID = Integer.parseInt(String.valueOf((int) (Math.random() * 100)));
                int customerID = Integer.parseInt(addAppointmentCustomerIDTextField.getText());

                if (localDateTimeStart.isAfter(localDateTimeEnd)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has start time after end time.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Appointment start time is after end time");
                    return;
                }

                if (localDateTimeStart.isEqual(localDateTimeEnd)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has same start and end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Appointment has same start and end time");
                    return;
                }

                for (Appointments appointments : getAllAppointments) {
                    LocalDateTime appointmentStartCheck = appointments.getAppointmentStart();
                    LocalDateTime appointmentEndCheck = appointments.getAppointmentEnd();

                    if ((customerID == appointments.getCustomerID()) &&
                            //(newAppointmentID != appointments.getAppointmentID()) &&
                            (localDateTimeStart.isBefore(appointmentStartCheck))
                            && (localDateTimeEnd.isAfter(appointmentEndCheck))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps existing appointment");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment overlaps an existing appointment");
                        return;
                    }

                    if ((customerID == appointments.getCustomerID()) &&
                            //(newAppointmentID != appointments.getAppointmentID()) &&
                            (localDateTimeStart.isAfter(appointmentStartCheck)) && (localDateTimeStart.isBefore(appointmentEndCheck))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment start time overlaps an existing appointment");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment start time overlaps an existing appointment");
                        return;
                    }

                    if (customerID == appointments.getCustomerID() &&
                            //(newAppointmentID != appointments.getAppointmentID()) &&
                            (localDateTimeEnd.isAfter(appointmentStartCheck)) && (localDateTimeEnd.isBefore(appointmentEndCheck))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment end time overlaps an existing appointment");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment end time overlaps an existing appointment");
                        return;
                    }
                }

                String insertStatementSQL = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

                JDBC.setPreparedStatement(JDBC.getConnection(), insertStatementSQL);
                PreparedStatement preparedStatement = JDBC.getPreparedStatement();
                //preparedStatement.setInt(1, newAppointmentID);
                preparedStatement.setString(1, addAppointmentTitleTextField.getText());
                preparedStatement.setString(2, addAppointmentDescriptionTextField.getText());
                preparedStatement.setString(3, addAppointmentLocationTextField.getText());
                preparedStatement.setString(4, addAppointmentTypeTextField.getText());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(startUTC));
                preparedStatement.setTimestamp(6, Timestamp.valueOf(endUTC));
                preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(8, "admin");
                preparedStatement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(10, "admin");
                preparedStatement.setInt(11, Integer.parseInt(addAppointmentCustomerIDTextField.getText()));
                preparedStatement.setInt(12, Integer.parseInt(contactDAO.findContactID(addAppointmentContactComboBox.getValue())));
                preparedStatement.setInt(13, Integer.parseInt(contactDAO.findContactID(addAppointmentUserIDTextField.getText())));

                preparedStatement.execute();
                System.out.println("Add appointment executed");
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Function to take program back to main screen
     *
     * @param event when the cancel button is clicked
     * @throws IOException if the program isnt able to move back to the main screen
     */
    @FXML
    public void addAppointmentCancelButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
}