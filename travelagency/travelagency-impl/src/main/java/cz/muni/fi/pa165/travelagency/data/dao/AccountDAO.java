package cz.muni.fi.pa165.travelagency.data.dao;

import cz.muni.fi.pa165.travelagency.data.dto.AccountDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Account;
import java.util.List;

/**
 *
 * @author Michal Jurc
 */
public interface AccountDAO {
    
    public void create(Account account);
    
    public Account get(Long id);
    
    public Account getByUsername(String username);
    
    public void update(Account account);
    
    public void delete(Account account);
    
    public List<Account> getAll();
    
}
