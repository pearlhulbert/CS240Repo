package dao;

import model.User;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Connection accessCon;

    public UserDAO(Connection con) {this.accessCon = con;}

    public void insert(User user) throws DataAccessException {
        /** method used to insert a user object into the database table using sql commands as string
         * @param user is the user object to be inserted
         * @return nothing
         */
    }

    public User find(String username) throws DataAccessException {
        /** method used to find a user in the database based on username.
         * These commands will also be in SQL
         * @param username is a string representing username
         * @return the user with this username
         */
        return null;
    }

    public void remove(User user) throws DataAccessException {
        /** this method removes a user from the database table
         * @param user user to be removed
         * @return nothing
         */
    }

    public Person findUserInTree(String personID) {
        /** method finds the user in their tree
         * @param personID identifies user's person
         * @return user as a Person
         */
        return null;
    }

}
