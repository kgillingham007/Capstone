package gillingham.capstone.model;

/**ReportMonth model class
 *
 */
public class ReportMonth {
    /**report month identifiers
     *
     */
    public String appointmentMonth;
    public int appointmentTotal;


    /**used to create new reportMonth
     *
     * @param appointmentMonth
     * @param appointmentTotal
     */
    public ReportMonth(String appointmentMonth , int appointmentTotal){
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotal = appointmentTotal;
    }

    /**used to get appointment month
     *
     * @return appointment month
     */
    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    /**used to set appointment month
     *
     * @param appointmentMonth
     */
    public void setAppointmentMonth(String appointmentMonth) {
        this.appointmentMonth = appointmentMonth;
    }

    /**used to get appointment total
     *
     * @return appointment total
     */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }

    /**used to set appointment total
     *
     * @param appointmentTotal
     */
    public void setAppointmentTotal(int appointmentTotal) {
        this.appointmentTotal = appointmentTotal;
    }
}
