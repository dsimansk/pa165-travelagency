package cz.muni.fi.pa165.travelagency.data.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * A class representing Vacation order entity.
 * 
 * @author David Simansky
 */
@Entity
public class VacationOrder implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToOne
    private Customer customer;
    
    @OneToOne
    private Vacation vacation;
    
    @OneToMany
    private List<Tour> tours;
    
    private int reservations;

    public VacationOrder() {
        tours = new ArrayList<>();
        reservations = 1;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }
    
    public void addTour(Tour tour) {
        tours.add(tour);
    }
    
    public void removeTour(Tour tour) {
        if (tours.contains(tour)) {
            tours.remove(tour);
        } else {
            throw new IllegalArgumentException("This vacation entity does not "
                    + "contain provided tour.");
        }
    }

    public int getReservations() {
        return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final VacationOrder other = (VacationOrder) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
     
}
