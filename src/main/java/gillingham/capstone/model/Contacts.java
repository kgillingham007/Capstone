package gillingham.capstone.model;

/**Model for Contacts
 *
 */
public class Contacts {
    /**Contact identifiers
     *
     */

    public int contactID;
    public String contactName;
    public String contactEmail;

    /**Creating contacts
     *
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contacts(int contactID , String contactName , String contactEmail){
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**Used to get contact ID
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

    /**Used to get contact name
     *
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**Used to set contact name
     *
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**Used to get contact email
     *
     * @return contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**Used to set contact email
     *
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
