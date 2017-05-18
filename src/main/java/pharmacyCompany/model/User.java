/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacyCompany.model;

import java.util.Objects;

/**
 *
 * @author Alumne
 */
public class User implements Entity {

    private int id;
    private String name;
    private String surname1;
    private String nick;
    private String password;
    private String address;
    private String telephone;
    private String mail;
    private String birthDate;
    private String entryDate;
    private String dropOutDate;
    private int active;
    private String image;
    private int userType;

    public User() {

    }

    public User(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }

    public User(String nick) {
        this.nick = nick;
    }

    public User(int id, String name, String surname1, String nick, String password, String address, String telephone, String mail, String birthDate, String entryDate, String dropOutDate, int active, String image, int userType) {
        this.id = id;
        this.name = name;
        this.surname1 = surname1;
        this.nick = nick;
        this.password = password;
        this.address = address;
        this.telephone = telephone;
        this.mail = mail;
        this.birthDate = birthDate;
        this.entryDate = entryDate;
        this.dropOutDate = dropOutDate;
        this.active = active;
        this.image = image;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDropOutDate() {
        return dropOutDate;
    }

    public void getDropOutDate(String dropOutDate) {
        this.dropOutDate = dropOutDate;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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
        final User other = (User) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + this.id + " nick=" + this.nick + ", name=" + this.name + ", surname1=" + this.surname1 + ", telephone=" + this.telephone + ", address=" + this.address + "}";
    }
}
