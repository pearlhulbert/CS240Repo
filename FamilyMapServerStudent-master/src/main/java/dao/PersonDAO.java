package dao;

import model.Event;
import model.Person;

import java.sql.*;
import java.util.List;

public class PersonDAO {

    private final Connection accessCon;

    public PersonDAO(Connection con) {this.accessCon = con;}

    /** method used to insert a person object into the database table using sql commands as string
     * @param person is the event object to be inserted
     */
    public void insert(Person person) throws DataAccessException {
        String sqlString = "INSERT INTO Person (PersonID, AssociatedUsername, FirstName, LastName, Gender, " +
                "FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = accessCon.prepareStatement(sqlString)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());

            if (person.getFatherID() != null) {
                stmt.setString(6, person.getFatherID());
            }
            if (person.getMotherID() != null) {
                stmt.setString(6, person.getMotherID());
            }
            if (person.getSpouseID() != null) {
                stmt.setString(6, person.getSpouseID());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /** method used to find a person in the database based on person ID.
     * These commands will also be in SQL
     * @param personID is a string representing eventID
     * @return the person matching this personID
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE PersonID = ?;";
        try (PreparedStatement stmt = accessCon.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /** this method removes a person from the database table
     * @param person person to be removed
     */
    public void remove(Person person) throws DataAccessException {
        String sqlString = "DELETE FROM Events WHERE PersonID = " + person.getPersonID();

        try (PreparedStatement stmt = accessCon.prepareStatement(sqlString)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setNull(1, Types.VARCHAR);
            stmt.setNull(2, Types.VARCHAR);
            stmt.setNull(3, Types.VARCHAR);
            stmt.setNull(4, Types.VARCHAR);
            stmt.setNull(5, Types.VARCHAR);
            stmt.setNull(6, Types.VARCHAR);
            stmt.setNull(7, Types.VARCHAR);
            stmt.setNull(8, Types.VARCHAR);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while removing from the database");
        }
    }

    public void clearPeople() throws DataAccessException {
        String sqlString = "DELETE FROM Person";

        try (PreparedStatement stmt = accessCon.prepareStatement(sqlString)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    /** method gets a person's family members based on their respective ids
     * @param personID person's id
     * @return list of people in the person's family
     */
    public List<Person> getFamily(String personID) {
        return null;
    }

    /** removes all people associated with a user
     * @param username user's username
     */
    public void removePeople(String username) {}
}
