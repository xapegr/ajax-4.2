/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacyCompany.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Alumne
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    //private final ViewInterface view;
    private int controllerType;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.controllerType = Integer.parseInt(request.getParameter("controllerType"));
        ArrayList<Object> outPutData = new ArrayList<>();
        switch (controllerType) {
            case 0:
                ControllerInterface controlUser = new UserController(request, response);
                outPutData = controlUser.doAction();
                break;
            case 1:
                ControllerInterface controlProduct = new ProductController(request, response);
                outPutData = controlProduct.doAction();
                break;
            case 2:
                ControllerInterface controlPurchase = new PurchaseController(request, response);
                outPutData = controlPurchase.doAction();
                break;
            default:
                outPutData.add(false);
                List<String> errors = new ArrayList<>();
                errors.add("There has been an error in the server, try later");
                outPutData.add(errors);
                System.out.println("Action in main controller not correct: " + controllerType);
        }

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");

        // Send data to client jackson mapper                       
        try {
            // 3. Send data to client jackson mapper
            mapper.writeValue(response.getOutputStream(), outPutData);
            //System.out.println ("Mensaje a mostrar: "+jsonObject.get("nick"));
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
