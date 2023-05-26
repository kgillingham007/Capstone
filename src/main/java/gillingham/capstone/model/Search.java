package gillingham.capstone.model;

import gillingham.capstone.Database.appointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Search {

    public static ObservableList<Customer> allCustomers = checkedObservableList();

    private static ObservableList<Customer> checkedObservableList() {
        return null;
    }

    public static ObservableList<Customer> allAppointments = checkedObservableList();

    public static ObservableList<Customer> lookUpCustomer_Name(String partialCustomerName){
        ObservableList<Customer> customerName = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = Customer.getAllCustomers();
        for (Customer customer : allCustomers){
            if (customer.getName().contains(partialCustomerName)){
                customerName.add(customer);
            }
        }
        return customerName;
    }

    public static ObservableList<Appointments> lookUpAppointment_Name(String partialAppointmentName) throws SQLException {
        ObservableList<Appointments> appointmentName = FXCollections.observableArrayList();
        ObservableList<Appointments> allAppointments = null;
        try {
            allAppointments = appointmentDAO.getAllAppointments();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        for (Appointments appointments : allAppointments){
            if (appointments.getAppointmentTitle().contains(partialAppointmentName)){
                appointmentName.add(appointments);
            }
        }
        return appointmentName;
    }
}
