package service;

import dao.*;
import handler.JsonFileReader;
import model.LocationArray;
import request.FillRequest;
import result.FillResult;
import model.Person;
import model.User;
import model.Event;
import result.LoginResult;
import model.Location;

import java.util.*;
import java.sql.Connection;

public class FillService {

    /** method fills the family tree according to the specified username and potential number of generations
     * @param fRequest the request with said required info
     * @return the result of that request
     */
   public FillResult fill(FillRequest fRequest) {
    //clear database of everything associated with user
        String userName = fRequest.getUsername();
        int generations = fRequest.getGenerations();
        FamilyTree newTree = new FamilyTree();
        String message;
        boolean success = false;
        Vector<Event> events = new Vector<>();
        Vector<Person> people = new Vector<>();
        User rootUser = new User();
       Database db = new Database();
       try {
           Connection connect = db.openConnection();

           UserDAO uDAO = new UserDAO(connect);
           PersonDAO pDAO = new PersonDAO(connect);
           EventDAO eDAO = new EventDAO(connect);

           rootUser = uDAO.find(userName);

           for (Person p : people) {
               pDAO.insert(p);
           }

           for (Event e : events) {
               eDAO.insert(e);
           }
           db.closeConnection(true);
       } catch(DataAccessException d) {
           d.printStackTrace();
           message = "Error: " + d.getMessage();
           try {
               db.closeConnection(false);
           } catch (DataAccessException e) {
               e.printStackTrace();
           }
           FillResult result = new FillResult(message, success);
           return result;
       }
       newTree.generateRoot(userName, generations, events, people, rootUser);
       message = "Successfuly added " + events.size() + " events and " + people.size() + " people.";
       success = true;
       FillResult result = new FillResult(message, success);
       return result;
    }

}
