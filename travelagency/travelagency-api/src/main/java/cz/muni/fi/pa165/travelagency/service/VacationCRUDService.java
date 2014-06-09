package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import java.util.List;

/**
 *
 * @author Michal Jurc
 */
public interface VacationCRUDService {
    
    public void create(VacationDTO vacation);
    
    public VacationDTO get(Long id);
    
    public void update(VacationDTO vacation);
    
    public void delete(VacationDTO vacation);
    
    public List<VacationDTO> getAll();
    
    public List<VacationDTO> getByDestination(String destination);
    
    public List<String> getAllDestinations();
    
}
