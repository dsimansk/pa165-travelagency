package cz.muni.fi.pa165.travelagency.data.dao;

import cz.muni.fi.pa165.travelagency.data.entity.Vacation;
import java.util.List;

/**
 * Represents Data Access Object for Vacation entity class. Consists of basic
 * CRUD operations and methods for advanced entry retrieval.
 *
 * @author Michal Jurc
 */
public interface VacationDAO {
    
    /**
     * Creates new vacation entity and persists it to database with 
     * autogenerated primary key.
     * 
     * @param vacation Vacation to be created.
     * @throws IllegalArgumentException If vacation is null, invalid or if it 
     * already has set id.
     */
    public void create(Vacation vacation);
    
    /**
     * Retrieves vacation from database by its primary key.
     * 
     * @param id Primary key of the vacation entity to be retrieved.
     * @return Vacation with provided id or null if there is no such entity.
     * @throws IllegalArgumentException If provided id is null.
     */
    public Vacation get(Long id);
    
    /**
     * Updates existing vacation entity in database.
     * 
     * @param vacation Vacation entity to be updated.
     * @throws IllegalArgumentException If provided vacation entity is null, 
     * invalid or if it is not already persisted in database.
     */
    public void update(Vacation vacation);
    
    /**
     * Removes existing vacation entity from database.
     * 
     * @param vacation Vacation entity to be removed.
     * @throws IllegalArgumentException If provided vacation entity is null, 
     * invalid or if it is not already persisted in database.
     */
    public void delete(Vacation vacation);
    
    /**
     * Retrieves all persisted vacation entities from database.
     * 
     * @return List of all persisted vacation entities from database.
     */
    public List<Vacation> getAll();
    
    
    public List<Vacation> getByDestination(String destination);
    
    public List<String> getAllDestinations();
    
}
