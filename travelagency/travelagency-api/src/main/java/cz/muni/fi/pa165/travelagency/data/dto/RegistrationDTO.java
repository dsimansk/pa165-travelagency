package cz.muni.fi.pa165.travelagency.data.dto;

/**
 *
 * @author Michal Jurc
 */
public class RegistrationDTO {
    
    private String name;
    private String username;
    private String password;
    private String address;
    private String phoneNumber;
    
    public RegistrationDTO() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNo) {
        this.phoneNumber = phoneNo;
    }
    
}