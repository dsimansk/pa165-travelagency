package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import java.util.List;

/**
 *
 * @author David Simansky
 */
public interface CustomerCRUDService {
    
    public void create(CustomerDTO customer);
    
    public CustomerDTO get(Long id);
    
    public void update(CustomerDTO customer);
    
    public void delete(CustomerDTO customer);
    
    public List<CustomerDTO> getAll();
    
}
