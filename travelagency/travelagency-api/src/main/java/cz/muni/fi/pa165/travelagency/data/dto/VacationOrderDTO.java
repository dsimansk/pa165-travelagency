package cz.muni.fi.pa165.travelagency.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class representing Data Transfer Object for Vacation Order entity.
 *
 * @author Sebastian Kunec
 */
public class VacationOrderDTO {
    
    private Long id;
    private CustomerDTO customer;
    private VacationDTO vacation;
    private List<Long> tours;
    private int reservations;
    
    public VacationOrderDTO() {
        this.tours = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public VacationDTO getVacation() {
        return vacation;
    }

    public void setVacation(VacationDTO vacation) {
        this.vacation = vacation;
    }

    public List<Long> getTours() {
        return tours;
    }

    public void setTours(List<Long> tours) {
        this.tours = tours;
    }

    public int getReservations() {
        return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final VacationOrderDTO other = (VacationOrderDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
