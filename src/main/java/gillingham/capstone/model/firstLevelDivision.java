package gillingham.capstone.model;

/**Model first level division class
 *
 */
public class firstLevelDivision {
    /**first level division identifiers
     *
     */
    private int Division_ID;
    private String Division;
    public int Country_ID;

    /**used to create first level division
     *
     * @param Division_ID
     * @param Division
     * @param Country_ID
     */
    public firstLevelDivision(int Division_ID , String Division , int Country_ID){
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Country_ID = Country_ID;
    }

    /**used to get division ID
     *
     * @return division ID
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**used to set division ID
     *
     * @param division_ID
     */
    public void setDivision_ID(int division_ID) {
        this.Division_ID = division_ID;
    }

    /**used to get division name
     *
     * @return division name
     */
    public String getDivisionName() {
        return Division;
    }

    /**used to set division name
     *
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.Division = divisionName;
    }

    /**used to get country ID
     *
     * @return country ID
     */
    public int getCountry_ID() {
        return Country_ID;
    }

    /**used to set country ID
     *
     * @param country_ID
     */
    public void setCountry_ID(int country_ID) {
        this.Country_ID = country_ID;
    }
}
