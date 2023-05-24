package gillingham.capstone.model;

/**Model for Country
 *
 */
public class Country {
    /**Country identifiers
     *
     */
    private int countryID;
    private String countryName;


    /**Creating country
     *
     * @param countryID
     * @param countryName
     */
    public Country(int countryID , String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**Used to get country ID
     *
     * @return country ID
     */
    public int getCountryID(){
        return countryID;
    }

    /**Used to get country name
     *
     * @return country name
     */
    public String getCountryName(){
        return countryName;
    }
}
