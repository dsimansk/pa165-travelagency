package cz.muni.fi.pa165.travelagencyclient.web.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A class representing Data Transfer Object for Tour entity.
 *
 * @author David Simansky
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourDTO {
    
    @JsonProperty
    private Long id;
    @JsonProperty
    private String destination;
    @JsonProperty
    private String description;
    @JsonProperty
    private Date startDate;
    @JsonProperty
    private int durationInHours;
    
    public TourDTO() { }
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final TourDTO other = (TourDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
