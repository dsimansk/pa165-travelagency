package cz.muni.fi.pa165.travelagency.service.impl;

import cz.muni.fi.pa165.travelagency.data.dao.AccountDAO;
import cz.muni.fi.pa165.travelagency.data.entity.Account;
import cz.muni.fi.pa165.travelagency.service.adapter.AccountDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michal Jurc
 */
@Service(value = "accountDetailsService")
@Transactional
public class AccountDetailsService implements UserDetailsService {
    
    @Autowired
    @Qualifier(value = "accountDAO")
    private AccountDAO accountDAO;
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        Account account = accountDAO.getByUsername(string);
        
        if (account == null) {
            throw new UsernameNotFoundException("Account not found.");
        }
        
        userDetails = new AccountDetailsAdapter(account);
        return userDetails;
    }
    
}
