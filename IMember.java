package CHC5223;

/**
 * DO NOT CHANGE THIS INTERFACE
 * You must create a class that implements this interface
 * <p>
 * objects of a class implementing this interface holds information
 * about a Member
 */

public interface IMember {

    /**
     * Retrieves the name of the member
     *
     * @return the name of the member
     * @pre true
     */
    public String getName();

    /**
     * Retrieves the member's affiliation
     *
     * @return the member's affiliation
     * @pre true
     */
    public String getAffiliation();

    /**
     * @return the member's name and affiliation as string
     */
    @Override
    public String toString();
}
