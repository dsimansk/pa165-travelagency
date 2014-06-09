package cz.muni.fi.pa165.travelagency.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A class representing Vacation entity.
 *
 * @author Martin Gerlasinsky
 */
@Entity
public class Vacation implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String destination;
    
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    private BigDecimal price;
    private int maxCapacity;
    private int reserved;
    
    @OneToMany(fetch=FetchType.EAGER)
    private List<Tour> tours;
    
    public Vacation() {
        reserved = 0;
        tours = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }
    
    public void reserve() {
        if (reserved < maxCapacity) {
            reserved++;
        } else {
            //throw new exception
        }
    }
    
    public void unreserve() {
        reserved--;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }
    
    public void addTour(Tour tour) {
        if (!tours.contains(tour)) {
            tours.add(tour);
        } else {
            throw new IllegalArgumentException("This vacation entity already "
                    + "contains provided tour.");
        }
    }
    
    public void removeTour(Tour tour) {
        if (tours.contains(tour)) {
            tours.remove(tour);
        } else {
            throw new IllegalArgumentException("This vacation entity does not "
                    + "contain provided tour.");
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Vacation other = (Vacation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
