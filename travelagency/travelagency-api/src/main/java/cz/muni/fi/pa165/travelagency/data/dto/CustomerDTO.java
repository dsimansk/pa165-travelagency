package cz.muni.fi.pa165.travelagency.data.dto;

import java.util.Objects;

/**
 * A class representing Data Transfer Object for Customer entity.
 *
 * @author Michal Jurc
 */
public class CustomerDTO {
    
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    
    public CustomerDTO() { }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
      
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        final CustomerDTO other = (CustomerDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
