package CHC5223;

/**
 * an objects of a class implementing this interface holds a
 * database of member information
 * <p>
 * DO NOT CHANGE THIS INTERFACE
 * You must create a class that implements this interface
 */

public interface IMemberDB {

    /**
     * Empties the database.
     *
     * @pre true
     */
    public void clearDB();

    /**
     * Determines whether a member's name exists as a key inside the database
     *
     * @param name the member name (key) to locate
     * @return true iff the name exists as a key in the database
     * @pre name is not null and not empty string
     */
    public boolean containsName(String name);

    /**
     * Returns a Member object mapped to the supplied name.
     *
     * @param name The Member name (key) to locate
     * @return the Member object mapped to the key name if the name
     * exists as key in the database, otherwise null
     * @pre name not null and not empty string
     */
    public Member get(String name);

    /**
     * Returns the number of members in the database
     *
     * @return number of members in the database.
     * @pre true
     */
    public int size();

    /**
     * Determines if the database is empty or not.
     *
     * @return true iff the database is empty
     * @pre true
     */
    public boolean isEmpty();

    /**
     * Inserts a Member object into the database, with the key of the supplied
     * member's name.
     * Note: If the name already exists as a key, then the original entry
     * is overwritten.
     * This method must return the previous associated value
     * if one exists, otherwise null
     *
     * @pre member not null and member name not empty string
     */
    public Member put(Member member);

    /**
     * Removes and returns a member from the database, with the key
     * the supplied name.
     *
     * @param name The name (key) to remove.
     * @return the removed member object mapped to the name, or null if
     * the name does not exist.
     * @pre name not null and name not empty string
     */
    public Member remove(String name);

    /**
     * Prints the names and affiliations of all the members in the database in
     * alphabetic order.
     *
     * @pre true
     */
    public void displayDB();
}
