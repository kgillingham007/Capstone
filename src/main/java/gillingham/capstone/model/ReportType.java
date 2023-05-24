package gillingham.capstone.model;

/**Report type model class
 *
 */
public class ReportType {
    /**report type identifiers
     *
     */
    public String appointmentType;
    public int appointmentTotal;

    /**used to create report type
     *
     * @param appointmentType
     * @param appointmentTotal
     */
    public ReportType(String appointmentType , int appointmentTotal){
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
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
