/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacyCompany.controller;

import pharmacyCompany.model.Entity;
import java.util.ArrayList;
import java.util.List;
import pharmacyCompany.model.User;
import pharmacyCompany.model.persist.UserADO;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Alumne
 */
public class UserController implements ControllerInterface {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public UserController() {

    }

    public UserController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

    }

    @Override
    public ArrayList<Object> doAction() {
        int action = Integer.parseInt(request.getParameter("action"));
        ArrayList<Object> outPutData = new ArrayList<>();

        if (request.getParameter("JSONData") != null) {
            try {
                // 1. get received JSON data from request
                String JSONData = request.getParameter("JSONData");
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(JSONData);

                // 2. Accés to database in order to get data  
                switch (action) {
                    case 10000:
                        outPutData = userConnection((String) jsonObject.get("nick"), (String) jsonObject.get("password"));
                        break;
                    case 10100:
                        //Get current date
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date currentdate = new Date();

                        User userInsert = new User((int) 0, (String) jsonObject.get("name"), (String) jsonObject.get("surname1"), (String) jsonObject.get("nick"),
                                (String) jsonObject.get("password"), (String) jsonObject.get("address"), (String) jsonObject.get("telephone"),
                                (String) jsonObject.get("mail"), (String) jsonObject.get("birthDate"), df.format(currentdate),
                                "0000-00-00", Integer.valueOf(jsonObject.get("active").toString()), (String) jsonObject.get("image"),
                                (int) jsonObject.get("userType"));

                        outPutData = userInsert(userInsert);

                        request.getSession().setAttribute("user", userInsert);
                        break;
                    case 10200:
                        User userMod = new User(Integer.valueOf(jsonObject.get("id").toString()), (String) jsonObject.get("name"),
                                (String) jsonObject.get("surname1"), (String) jsonObject.get("nick"), (String) jsonObject.get("password"),
                                (String) jsonObject.get("address"), (String) jsonObject.get("telephone"), (String) jsonObject.get("mail"),
                                (String) jsonObject.get("birthDate"), (String) jsonObject.get("entryDate"), (String) jsonObject.get("dropOutDate"),
                                Integer.valueOf(jsonObject.get("active").toString()), (String) jsonObject.get("image"),
                                Integer.valueOf(jsonObject.get("userType").toString()));

                        outPutData = userMod(userMod);
                        break;
                    case 10300:
                        outPutData = nickChecking((String) jsonObject.get("nick"));
                        break;
                    case 10400:
                        outPutData = sessionControl();
                        break;
                    case 10500:
                        outPutData = sessionLogOut();
                        break;
                    default:
                        //Sending to client the error                        
                        outPutData.add(false);
                        List<String> errors = new ArrayList<>();
                        errors.add("There has been an error in the server, try later");
                        outPutData.add(errors);
                        //view.setFormattedDataTosend(outPutData);
                        System.out.println("Action is not correct in userController, action: " + action);
                        break;
                }
            } catch (ParseException ex) {
                System.out.println("Message: " + ex.getMessage());
            }
        }

        return outPutData;
    }

    private synchronized ArrayList<Object> userConnection(String nick, String password) {
        UserADO helper;
        ArrayList<Object> outPutData = new ArrayList<>();

        try {
            helper = new UserADO();

            Collection<Entity> list = helper.loggin(new User(nick, password));
            if (list == null || list.isEmpty()) {
                outPutData.add(false);
                List<String> errors = new ArrayList<>();
                errors.add("Either User or password is not correct");
                outPutData.add(errors);
            } else {
                outPutData.add(true);
                outPutData.add(list);

                User user = (User) list.toArray()[0];

                //Starting session
                //HttpSession session = request.getSession();
                //session.setAttribute("idUser", user.getId());
                //request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("user", user);
            }

        } catch (IOException | ClassNotFoundException ex) {
            outPutData.add(false);
            List<String> errors = new ArrayList<>();
            errors.add("There has been an error in the server, try later");
            outPutData.add(errors);
            System.out.println("Internal error while connecting (userConnection): " + ex);
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return outPutData;
    }

    private synchronized ArrayList<Object> nickChecking(String nick) {
        UserADO helper;
        ArrayList<Object> outPutData = new ArrayList<>();

        try {
            helper = new UserADO();

            Collection<Entity> list = helper.findByNick(new User(nick));
            if (list == null || list.isEmpty()) {
                outPutData.add(false);
                List<String> errors = new ArrayList<>();
                errors.add("No user found with the nick: " + nick);
                outPutData.add(errors);
            } else {
                outPutData.add(true);
                outPutData.add(list);
            }

        } catch (IOException | ClassNotFoundException ex) {
            outPutData.add(false);
            List<String> errors = new ArrayList<>();
            errors.add("There has been an error in the server, try later");
            outPutData.add(errors);
            System.out.println("Internal error while nick checking (nickChecking): " + ex);
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return outPutData;
    }

    private synchronized ArrayList<Object> userInsert(User user) {
        UserADO helper;
        ArrayList<Object> outPutData = new ArrayList<>();

        try {
            helper = new UserADO();

            int rowsAffected = helper.insert(user);
            if (rowsAffected == 0) {
                outPutData.add(false);
                List<String> errors = new ArrayList<>();
                errors.add("Usert not correctly inserted");
                outPutData.add(errors);
            } else {
                Collection<Entity> list = helper.findByNick(user);
                if (list == null || list.isEmpty()) {
                    outPutData.add(false);
                    List<String> errors = new ArrayList<>();
                    errors.add("Error finding user by nick");
                    outPutData.add(errors);
                } else {
                    outPutData.add(true);
                    outPutData.add(list);
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            outPutData.add(false);
            List<String> errors = new ArrayList<>();
            errors.add("There has been an error in the server, try later");
            outPutData.add(errors);
            System.out.println("Internal error while creating new user (userInsert): " + ex);
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return outPutData;
    }

    private synchronized ArrayList<Object> userMod(User user) {
        UserADO helper;
        ArrayList<Object> outPutData = new ArrayList<>();

        try {
            helper = new UserADO();

            int rowsAffected = helper.update(user);
            if (rowsAffected == 0) {
                outPutData.add(false);
                List<String> errors = new ArrayList<>();
                errors.add("Usert not correctly modified");
                outPutData.add(errors);
            } else {
                outPutData.add(true);
                List<String> errors = new ArrayList<>();
                outPutData.add(errors);
            }
        } catch (IOException | ClassNotFoundException ex) {
            outPutData.add(false);
            List<String> errors = new ArrayList<>();
            errors.add("There has been an error in the server, try later");
            outPutData.add(errors);
            System.out.println("Internal error while modifying new user (userMod): " + ex);
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return outPutData;
    }

    private synchronized ArrayList<Object> sessionControl() {
        ArrayList<Object> outPutData = new ArrayList<>();

        if (request.getSession().getAttribute("user") != null) {
            outPutData.add(true);
            outPutData.add(request.getSession().getAttribute("user"));
        } else {
            outPutData.add(false);
        }

        return outPutData;
    }

    private ArrayList<Object> sessionLogOut() {
        ArrayList<Object> outPutData = new ArrayList<>();

        request.getSession().removeAttribute("user");
        outPutData.add(true);

        return outPutData;
    }
}
