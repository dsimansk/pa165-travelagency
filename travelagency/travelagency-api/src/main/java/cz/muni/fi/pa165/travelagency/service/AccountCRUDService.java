package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dto.AccountDTO;
import java.util.List;

/**
 *
 * @author Michal Jurc
 */
public interface AccountCRUDService {
    
    public void create(AccountDTO account);
    
    public AccountDTO get(Long id);
    
    public AccountDTO getByUsername(String username);
    
    public void update(AccountDTO accountDTO);
    
    public void delete(AccountDTO accountDTO);
    
    public List<AccountDTO> getAll();
    
    public void validateDuplicity(AccountDTO accountDTO);
    
}
