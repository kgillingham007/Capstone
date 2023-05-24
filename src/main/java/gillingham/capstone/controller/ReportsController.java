package gillingham.capstone.controller;

import gillingham.capstone.Database.appointmentDAO;
import gillingham.capstone.Database.contactDAO;
import gillingham.capstone.Database.reportDAO;
import gillingham.capstone.model.*;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

/**Controller class for the report screen
 *
 */
public class ReportsController {

    /**Labels for the Reports screen
     *
     */
    // ******** Contact info table related ********
    @FXML
    private TableView reportsContactInformationTableView;
    @FXML private TableColumn<?,?> reportsContactInformationIDColumn;
    @FXML private TableColumn<?,?> reportsContactInformationTitleColumn;
    @FXML private TableColumn<?,?> reportsContactInformationDescriptionColumn;
    @FXML private TableColumn<?,?> reportsContactInformationTypeColumn;
    @FXML private TableColumn<?,?> reportsContactInformationStartColumn;
    @FXML private TableColumn<?,?> reportsContactInformationEndColumn;

    @FXML private TableColumn<?,?> reportsContactInformationCustomerIDColumn;


    // ******** Appointments per Month table related ********
    @FXML
    private TableView reportsAppointmentsMonthTotalTableView;
    @FXML private TableColumn<?,?> reportsAppointmentsMonthTotalMonthColumn;
    @FXML private TableColumn<?,?> reportsAppointmentsMonthTotalTotalColumn;


    // ******** Appointments per Type table related ********
    @FXML private TableView reportsAppointmentsTypeTotalTableView;
    @FXML private TableColumn<?,?> reportsAppointmentsTypeTotalTypeColumn;
    @FXML private TableColumn<?,?> reportsAppointmentsTypeTotalTotalColumn;


    // ******** Customer per Country table related ********
    @FXML
    private TableView reportsCustomerCountryTableView;
    @FXML private TableColumn<?,?> reportsCustomerCountryCountryColumn;
    @FXML private TableColumn<?,?> reportsCustomerCountryTotalColumn;


    // ******** Misc related ********
    @FXML private Button reportsBackButton;
    @FXML private Button reportsExitButton;
    @FXML private ComboBox reportsSelectContactComboBox;

    private ObservableList<Appointments> appointments;


    /**Used to initialize the data for all 3 auxiliary tables. The main table is initialized later
     *
     * @throws SQLException if there is an issue accessing the information from the database
     */
    public void initialize() throws SQLException{
        reportsContactInformationIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        reportsContactInformationTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        reportsContactInformationDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        reportsContactInformationTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        reportsContactInformationStartColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        reportsContactInformationEndColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        reportsContactInformationCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        reportsAppointmentsMonthTotalMonthColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        reportsAppointmentsMonthTotalTotalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        reportsAppointmentsTypeTotalTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        reportsAppointmentsTypeTotalTotalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        reportsCustomerCountryCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        reportsCustomerCountryTotalColumn.setCellValueFactory(new PropertyValueFactory<>("countryCount"));

        ObservableList<Contacts> contactsObservableList = contactDAO.getAllContacts();
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        contactsObservableList.forEach(contacts -> allContactNames.add(contacts.getContactName()));
        reportsSelectContactComboBox.setItems(allContactNames);

        /*try {
            int contactID = 0;
            ObservableList<Appointments> getAllAppointmentsList = appointmentDAO.getAllAppointments();
            ObservableList<Appointments> appointmentsInfo = FXCollections.observableArrayList();
            ObservableList<Contacts> getAllContacts = contactDAO.getAllContacts();
            Appointments contactAppointmentInfo;
            //String contactName = (String) reportsSelectContactComboBox.getSelectionModel().getSelectedItem();
            *//*for (Contacts contacts : getAllContacts){
                if (contactName.equals(contacts.getContactName())){
                    contactID = contacts.getContactID();
                }
            }*//*
            String contactName = (String) reportsSelectContactComboBox.getSelectionModel().getSelectedItem();
            *//*if (selectedItem != null) {
                String contactName = (String) selectedItem;*//*
                for (Contacts contacts : getAllContacts){
                    if (contactName.equals(contacts.getContactName())){
                        contactID = contacts.getContactID();
                    }
                }
            //}
            for (Appointments appointments : getAllAppointmentsList){
                if (appointments.getContactID() == contactID){
                    contactAppointmentInfo = appointments;
                    appointmentsInfo.add(contactAppointmentInfo);
                }
            }
            reportsContactInformationTableView.setItems(appointmentsInfo);
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }*/

        try {
            ObservableList<Appointments> getAllAppointments = appointmentDAO.getAllAppointments();
            ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
            ObservableList<Month> eachAppointmentMonth = FXCollections.observableArrayList();

            ObservableList<String> appointmentType = FXCollections.observableArrayList();
            ObservableList<String> eachAppointment = FXCollections.observableArrayList();

            ObservableList<ReportType> reportType = FXCollections.observableArrayList();
            ObservableList<ReportMonth> reportMonth = FXCollections.observableArrayList();

            getAllAppointments.forEach(appointments -> appointmentType.add(appointments.getAppointmentType()));

            getAllAppointments.stream().map(appointments -> {
                return appointments.getAppointmentStart().getMonth();
            }).forEach(appointmentMonths::add);

            appointmentMonths.stream().filter(month -> {
                return !eachAppointmentMonth.contains(month);
            }).forEach(eachAppointmentMonth::add);

            for (Appointments appointments : getAllAppointments){
                String reportsAppointmentType = appointments.getAppointmentType();
                if (!eachAppointment.contains(reportsAppointmentType)){
                    eachAppointment.add(reportsAppointmentType);
                }
            }
            for (Month month : appointmentMonths){
                int totalMonths = Collections.frequency(appointmentMonths, month);
                //ReportMonth existingMonth = null;
                String monthName = month.name();
                ReportMonth appointmentMonth = new ReportMonth(monthName, totalMonths);
                reportMonth.add(appointmentMonth);
            }
            reportsAppointmentsMonthTotalTableView.setItems(reportMonth);



            for (String type : eachAppointment){
                String typeToSet = type;
                int typeTotal = Collections.frequency(appointmentType , type);
                ReportType appointmentTypes = new ReportType(typeToSet , typeTotal);
                reportType.add(appointmentTypes);
            }
            reportsAppointmentsTypeTotalTableView.setItems(reportType);
            /*for (Month month : eachAppointmentMonth){
                String typeToSet = month.toString();
                int typeTotal = Collections.frequency(appointmentType , typeToSet);
                ReportType appointmentTypes = new ReportType(typeToSet, typeTotal);
                reportType.add(appointmentTypes);
            }*/


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            ObservableList<Report> totalCountries = reportDAO.getCountries();
            ObservableList<Report> countriesToTotal = FXCollections.observableArrayList();

            countriesToTotal.addAll(totalCountries);
            reportsCustomerCountryTableView.setItems(countriesToTotal);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**Used to populate the main table with the information related to the selected contact in contact combo box
     *
     * @param event when the combo box is selected
     * @throws IOException if there is an issue with the combo box
     */
    @FXML public void reportsSelectContactComboBoxClicked(ActionEvent event) throws IOException{
        try {
            int contactID = 0;
            ObservableList<Appointments> getAllAppointmentsList = appointmentDAO.getAllAppointments();
            ObservableList<Appointments> appointmentsInfo = FXCollections.observableArrayList();
            ObservableList<Contacts> getAllContacts = contactDAO.getAllContacts();
            Appointments contactAppointmentInfo;
            String contactName = (String) reportsSelectContactComboBox.getSelectionModel().getSelectedItem();
            for (Contacts contacts : getAllContacts){
                if (contactName.equals(contacts.getContactName())){
                    contactID = contacts.getContactID();
                }
            }
            for (Appointments appointments : getAllAppointmentsList){
                if (appointments.getContactID() == contactID){
                    contactAppointmentInfo = appointments;
                    appointmentsInfo.add(contactAppointmentInfo);
                }
            }
            reportsContactInformationTableView.setItems(appointmentsInfo);
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /*@FXML public void appointmentMonthTypeTotals(){
        try {
            ObservableList<Appointments> getAllAppointments = appointmentDAO.getAllAppointments();
            ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
            ObservableList<Month> eachAppointmentMonth = FXCollections.observableArrayList();

            ObservableList<String> appointmentType = FXCollections.observableArrayList();
            ObservableList<String> eachAppointment = FXCollections.observableArrayList();

            ObservableList<ReportType> reportType = FXCollections.observableArrayList();
            ObservableList<ReportMonth> reportMonth = FXCollections.observableArrayList();

            getAllAppointments.forEach(appointments -> appointmentType.add(appointments.getAppointmentType()));

            getAllAppointments.stream().map(appointments -> appointments.getAppointmentStart().getMonth()).forEach(appointmentMonths::add);

            appointmentMonths.stream().filter(month -> !eachAppointmentMonth.contains(month)).forEach(eachAppointmentMonth::add);

            for (Appointments appointments : getAllAppointments){
                String reportsAppointmentType = appointments.getAppointmentType();
                if (!eachAppointment.contains(reportsAppointmentType)){
                    eachAppointment.add(reportsAppointmentType);
                }
            }
            for (Month month : appointmentMonths){
                int totalMonths = Collections.frequency(appointmentMonths, month);
                String monthName = month.name();
                ReportMonth appointmentMonth = new ReportMonth(monthName, totalMonths);
                reportMonth.add(appointmentMonth);
            }
            reportsAppointmentsMonthTotalTableView.setItems(reportMonth);

            for (String type : eachAppointment){
                String typeToSet = type;
                int typeTotal = Collections.frequency(appointmentType , type);
                ReportType appointmentTypes = new ReportType(typeToSet , typeTotal);
                reportType.add(appointmentTypes);
            }
            for (Month month : eachAppointmentMonth){
                String typeToSet = month.toString();
                int typeTotal = Collections.frequency(appointmentType , typeToSet);
                ReportType appointmentTypes = new ReportType(typeToSet, typeTotal);
                reportType.add(appointmentTypes);
            }

            reportsAppointmentsTypeTotalTableView.setItems(reportType);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }*/

    /*@FXML public void customerCountryTotal(){
        try {
            ObservableList<Report> totalCountries = reportDAO.getCountries();
            ObservableList<Report> countriesToTotal = FXCollections.observableArrayList();

            countriesToTotal.addAll(totalCountries);
            reportsCustomerCountryTableView.setItems(countriesToTotal);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }*/


    /**Send the screen back to the main screen
     *
     * @param event when the button is pressed
     * @throws IOException if there is an issue accessing the main screen
     */
    @FXML public void reportsBackButtonClicked(ActionEvent event ) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gillingham/capstone/mainscreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

    /**Used to exit the entire program
     *
     * @param event when the button is pressed
     * @throws IOException if there is an issue exiting the program
     */
    @FXML public void reportsExitButtonClicked(ActionEvent event) throws IOException{
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