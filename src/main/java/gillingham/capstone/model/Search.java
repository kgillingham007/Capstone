package gillingham.capstone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;

public class Search {

    public static ObservableList<Customer> allCustomers = FXCollections.checkedObservableList();

    public static ObservableList<Appointments> allAppointments = FXCollections.checkedObservableList();

    public static ObservableList<Customer> lookUpCustomer_Name(String partialName){
        ObservableList<Customer> customerName = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = Customer.getAllCustomers();
        for (Customer customer : allCustomers){
            if (customer.getName().contains(partialName)){
                customerName.add(customer);
            }
        }
        return customerName;
    }

    public static ObservableList<Appointments> lookUpAppointment_Name(String partialName){
        ObservableList<Appointments> appointmentName = FXCollections.observableArrayList();
        ObservableList<Appointments> allAppointments = Appointments.getAllAppointments();
        for (Appointments appointments : allAppointments){
            if (appointments.getName().contains(partialName)){
                appointmentName.add(appointments);
            }
        }
        return appointmentName;
    }
}
