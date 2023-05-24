package gillingham.capstone.controller;

import gillingham.capstone.Database.JDBC;
import gillingham.capstone.Database.appointmentDAO;
import gillingham.capstone.Database.userDAO;
import gillingham.capstone.model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**Controller class for the Login screen
 *
 */
public class LoginController implements Initializable {

    /**Labels for the login controller
     *
     */
    @FXML
    private Label loginScreenUsernameLabel;
    @FXML
    private Label loginScreenPasswordLabel;
    @FXML
    private TextField loginScreenUsernameTextField;
    @FXML
    private TextField loginScreenPasswordTextField;
    @FXML
    private Label loginScreenTimeZoneLabel;
    @FXML
    private Button loginScreenLoginButton;
    @FXML
    private Button loginScreenClearButton;
    @FXML
    private Button loginScreenExitButton;
    @FXML
    private Label loginScreenTimeZoneDisplayLabel;
    Stage stage;


    /**Used to initialize the login screen
     *
     * @param url
     * @param resourceBundle used to automatically change language. English or French based on user settings
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale userLocale = Locale.getDefault();
        Locale.setDefault(userLocale);
        ZoneId zone = ZoneId.systemDefault();
        Connection connection = JDBC.openConnection();


        loginScreenTimeZoneDisplayLabel.setText(ZoneId.systemDefault().toString());
        resourceBundle = ResourceBundle.getBundle("Language/language", Locale.getDefault());
        loginScreenUsernameLabel.setText(resourceBundle.getString("username"));
        loginScreenPasswordLabel.setText(resourceBundle.getString("password"));
        loginScreenLoginButton.setText(resourceBundle.getString("login"));
        loginScreenClearButton.setText(resourceBundle.getString("clear"));
        loginScreenExitButton.setText(resourceBundle.getString("exit"));
        loginScreenTimeZoneLabel.setText(resourceBundle.getString("timezone"));
        //loginScreenTimeZoneLabel.setText(resourceBundle.getString("Timezone"));



        /*loginScreenUsernameLabel.setText("Username");
        loginScreenPasswordLabel.setText("Password");
        loginScreenLoginButton.setText("Login");
        loginScreenClearButton.setText("Clear");
        loginScreenExitButton.setText("Exit");*/



        //loginScreenTimeZoneLabel.setText(String.valueOf(zone));
    }



    /** When the login screen is pressed. checks users login and password and also writes to a file the user and timestamps
     *
     * @param event when the login button is clicked
     * @throws IOException if there is an issue changing screens
     * @throws SQLException if there is an issue accessing the database to verify user login info
     * @throws Exception if the user login info doesn't match database
     */
    @FXML public void loginScreenLoginButtonPressed(ActionEvent event) throws IOException, SQLException, Exception {
        try{
            ObservableList<Appointments> getAllAppointments = appointmentDAO.getAllAppointments();
            LocalDateTime currentTimeMinus15 = LocalDateTime.now().minusMinutes(15);
            LocalDateTime currentTimePlus15 = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime;
            int getAppointmentID = 0;
            LocalDateTime displayTime = null;
            boolean appointmentWithin15 = false;
            Locale userLocale = Locale.getDefault();
            ZoneId localZone = ZoneId.systemDefault();


            ResourceBundle rb = ResourceBundle.getBundle("Language/language", userLocale);

            String usernameInput = loginScreenUsernameTextField.getText();
            String userPasswordInput = loginScreenPasswordTextField.getText();
            int userID = userDAO.validateUser(usernameInput , userPasswordInput);

            FileWriter fileWriter = new FileWriter("Login_Activity.txt",true);
            PrintWriter outputFile = new PrintWriter(fileWriter);

            if (userID > 0){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/gillingham/softwaretwoc195/mainscreen.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage = (Stage) loginScreenLoginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();



                outputFile.print("user: " + usernameInput + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

                for (Appointments appointments: getAllAppointments){
                    //startTime = appointments.getAppointmentStart().toLocalTime();
                    startTime = appointments.getAppointmentStart().atZone(ZoneId.of("UTC")).withZoneSameInstant(localZone).toLocalDateTime();
                    if ((startTime.isAfter(currentTimeMinus15) || startTime.isEqual(currentTimeMinus15)) && (startTime.isBefore(currentTimePlus15) || (startTime.isEqual(currentTimePlus15)))){
                        getAppointmentID = appointments.getAppointmentID();
                        displayTime = startTime;
                        appointmentWithin15 = true;
                    }
                }

                if (appointmentWithin15){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " +getAppointmentID + " appointment start time is: " + displayTime);
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("There is an appointment within 15 minutes");
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("no upcoming appointments");
                }
            }
            else if (userID < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("incorrect"));
                Optional<ButtonType> confirmation = alert.showAndWait();
                //alert.setTitle("Error");
                //alert.setContentText("Incorrect Username or Password");
                //alert.show();

                outputFile.print("user: " + usernameInput + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
            }
            outputFile.close();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }

    }

    /**Used to clear the username and password fields on the login screen
     *
     * @param event when the clear button is clicked
     * @throws IOException if there is an issue clearing the text fields
     */
    @FXML public void loginScreenClearButtonPressed(ActionEvent event) throws IOException {
        loginScreenUsernameTextField.clear();
        loginScreenPasswordTextField.clear();
    }

    /** Used to exit the program
     *
     * @param event when the exit button is pressed
     * @throws IOException if there is an issue exiting the program
     */
    @FXML public void loginScreenExitButtonPressed(ActionEvent event) throws IOException {
        //LogonSession.logOff();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(((Node) event.getSource()).getScene().getWindow());
        alert.setHeaderText("Are you sure you want to Exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            System.exit(0);
        }
        //System.exit(0);
    }

}