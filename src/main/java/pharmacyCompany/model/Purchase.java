/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacyCompany.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Alumne
 */
public class Purchase implements Entity {

    private int id;
    private int idUser;
    private int idProduct;
    private String deliveryDate;
    private String specialRequests;
    private String specialInstructions;

    public Purchase() {

    }

    public Purchase(int id, int idUser, int idProduct, String deliveryDate, String specialRequests, String specialIntructions) {
        this.id = id;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.deliveryDate = deliveryDate;
        this.specialRequests = specialRequests;
        this.specialInstructions = specialIntructions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
    
    public String getDeliveryDate() {
        return deliveryDate;
    }
    
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    public String getSpecialRequests() {
        return specialRequests;
    }
    
    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Purchase other = (Purchase) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Purchase{" + this.id + " idUser=" + this.idUser + ", idProduct=" + this.idProduct + ", deliveryDate=" + this.deliveryDate + "}";
    }
}
