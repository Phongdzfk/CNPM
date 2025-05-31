package model;
import java.io.Serializable;

public class User implements Serializable{
    private int ID;
    private String Username;
    private String password;
    private String fullName;
    private String Role;
    private String phoneNumber;
    private String emailAddress;

    public User() {
        super();
    }

    public User(int ID, String username, String password, String fullName, String role, String phoneNumber, String emailAddress) {
        super();
        this.ID = ID;
        Username = username;
        this.password = password;
        this.fullName = fullName;
        Role = role;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
