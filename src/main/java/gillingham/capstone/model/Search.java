package gillingham.capstone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;

import static javafx.collections.FXCollections.checkedObservableList;

public class Search {

    public static ObservableList<Customer> allCustomers = checkedObservableList();

    public static ObservableList<Appointments> allAppointments = checkedObservableList();

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

    public static ObservableList<Appointments> lookUpAppointment_Name(String partialAppointmentName){
        ObservableList<Appointments> appointmentName = FXCollections.observableArrayList();
        ObservableList<Appointments> allAppointments = Appointments.getAllAppointments();
        for (Appointments appointments : allAppointments){
            if (appointments.getName().contains(partialAppointmentName)){
                appointmentName.add(appointments);
            }
        }
        return appointmentName;
    }
}
