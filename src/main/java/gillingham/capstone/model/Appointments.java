package gillingham.capstone.model;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Collection;

/**Appointment Class
 *
 */
public class Appointments {

    private static ObservableList<Appointments> allAppointments;
    /**Appointment Identifiers
     *
     */
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentType;
    private String appointmentDescription;
    private String appointmentLocation;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    public int contactID;
    public int customerID;
    public int userID;
    private Collection<Object> name;

    /**Creating appointments
     *
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param appointmentStart
     * @param appointmentEnd
     * @param contactID
     * @param customerID
     * @param userID
     */
    public Appointments(int appointmentID, String appointmentTitle, String appointmentDescription,
                        String appointmentLocation ,String appointmentType ,
                        LocalDateTime appointmentStart,
                        LocalDateTime appointmentEnd , int customerID,
                        int userID , int contactID){

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;


    }

    public static ObservableList<Appointments> getAllAppointments() {
        return allAppointments;
    }

    public static void setAllAppointments(ObservableList<Appointments> allAppointments) {
        Appointments.allAppointments = allAppointments;
    }


    /**Used to get appointment ID
     *
     * @returns appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }


    /**Used to set appointment ID
     *
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }


    /**Used to get appointment title
     *
     * @return appointment title
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }


    /**used to set appointment title
     *
     * @param appointmentTitle
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }


    /**used to get appointment type
     *
     * @return appointment type
     */
    public String getAppointmentType() {
        return appointmentType;
    }


    /**used to set appointment type
     *
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }


    /**used to get appointment description
     *
     * @return appointment description
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }


    /**used to set appointment description
     *
     * @param appointmentDescription
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }


    /**Used to get appointment location
     *
     * @return appointment location
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }


    /**Used to set appointment location
     *
     * @param appointmentLocation
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }


    /**used to get appointment start time
     *
     * @return appointment start time
     */
    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }


    /**used to set appointment start time
     *
     * @param appointmentStart
     */
    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }


    /**used to get appointment end time
     *
     * @return appointment end time
     */
    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }


    /**used to set appointment end time
     *
     * @param appointmentEnd
     */
    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    /**used to get customer ID
     *
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }


    /**Used to set customer ID
     *
     * @param customerID
     */
    public void setCustomer_ID(int customerID) {
        this.customerID = customerID;
    }

    /**Used to get user ID
     *
     * @return
     */
    public int getUserID() {
        return userID;
    }


    /**Used to set user ID
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**used to get contact ID
     *
     * @return contact ID
     */
    public int getContactID() {
        return contactID;
    }


    /**Used to set contact ID
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


    public Collection<Object> getName() {
        return name;
    }

    public void setName(Collection<Object> name) {
        this.name = name;
    }
}
