package model;

//modele du User avec ses champs, constructeur, getter et setter
public class User {

    private String username;
    private Number phone_number;
    private String address;
    private String email;
    private String password;
    private String role;
    public User(){}

    public User(String username, String address, String email, String password, String role, Number num) {
        this.username = username;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role=role;
        this.phone_number=num;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone_number(Number phone_number) {
        this.phone_number = phone_number;
    }

    public Number getPhone_number() {
        return phone_number;
    }
}
