package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dto.VacationOrderDTO;
import java.util.List;

/**
 *
 * @author Sebastian Kunec
 */
public interface VacationOrderCRUDService {
    
    public void create(VacationOrderDTO vacationOrder);
    
    public VacationOrderDTO get(Long id);
    
    public void update(VacationOrderDTO vacationOrder);
    
    public void delete(VacationOrderDTO vacationOrder);
    
    public List<VacationOrderDTO> getAll();
    
    public List<VacationOrderDTO> getOrdersByCustomer(Long customerId);
    
}
