package cz.muni.fi.pa165.travelagency.data.dao;

import cz.muni.fi.pa165.travelagency.data.entity.Tour;
import java.util.List;

/**
 * Represents DAO class for Tour class objects stored in database
 * Consists of basic CRUD operations and methods for advanced entries retrieval
 * @author Martin Gerlasinsky
 */
public interface TourDAO {
    
    /**
     * Creates new tour in database. Id is autogenerated by DB.
     * @param tour to be created
     * @throws IllegalArgumentException when tour is null or has alredy assigned id
     */
    public void create(Tour tour);
    
    /**
     * Retrieves tour with given ID from database
     * @param id of tour to be retrieved
     * @return tour with given id or null if doesn't exist
     * @throws IllegalArgumentException when id is null
     */
    public Tour get(Long id);
    
    /**
     * Updates existing tour in database
     * @param tour updated entry to be stored into database
     * @throws IllegalArgumentException if tour is null, has null id or doen't exist
     */
    public void update(Tour tour);
    
    /**
     * Deletes tour from database
     * @param tour to be removed
     * @throws IllegalArgumentException if tour is null, has null id or doen't exist
     */
    public void delete(Tour tour);
    
    /**
     * Retrieves all tour entries stored in database
     * @return list all tours from database
     */
    public List<Tour> getAll();
    
    
}
