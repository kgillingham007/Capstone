package gillingham.capstone.model;

/**Report model class
 *
 */
public class Report {
    /**Report identifiers
     *
     */
    private String countryName;
    private int countryCount;
    public String appointmentMonth;
    public int appointmentTotal;

    /**used to create a new report
     *
     * @param countryName
     * @param countryCount
     */
    public Report (String countryName , int countryCount){
        this.countryName = countryName;
        this.countryCount = countryCount;
    }

    /**used to get country name
     *
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**used to set country name
     *
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**used to get country count
     *
     * @return country count
     */
    public int getCountryCount() {
        return countryCount;
    }

    /**used to set country count
     *
     * @param countryCount
     */
    public void setCountryCount(int countryCount) {
        this.countryCount = countryCount;
    }
}
