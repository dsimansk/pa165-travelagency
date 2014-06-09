package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import java.util.List;

/**
 *
 * @author Martin Gerlasinsky
 */
public interface TourCRUDService {
    
    public void create(TourDTO tour);
    
    public TourDTO get(Long id);
    
    public void update(TourDTO tour);
    
    public void delete(TourDTO tour);
    
    public List<TourDTO> getAll();
    
}
