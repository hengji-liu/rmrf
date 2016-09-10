package bean;

import java.io.Serializable;

/**
 * Created by Linus on 10/09/2016.
 */
public class User implements Serializable{

    private String username;
    private String ps;
    private int identity;
    private String firstname;
    private String lastname;
    private String email;
    private String birthday;
    private String address;
    private String creditcard;
    private byte[] profileImg;

    public User(){

    }

    public User(String username, String firstname, String lastname, String email, String birthday, String address) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", ps='" + ps + '\'' +
                ", identity=" + identity +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", creditcard='" + creditcard + '\'' +
                '}';
    }

}
