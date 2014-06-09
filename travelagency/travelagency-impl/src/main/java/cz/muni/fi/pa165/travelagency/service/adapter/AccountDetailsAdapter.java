package cz.muni.fi.pa165.travelagency.service.adapter;

import cz.muni.fi.pa165.travelagency.data.entity.Account;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Michal Jurc
 */
public class AccountDetailsAdapter extends User {
    
    private final Long customerId;
    
    public AccountDetailsAdapter(Account account) {
        super(account.getName(), account.getPassword(), account.isEnabled(), 
                true, true, true, toAuthorities(account.getRoles()));
        this.customerId = account.getCustomer().getId();
    }
    
    private static Set<GrantedAuthority> toAuthorities(Set<String> roles) {
        Set<GrantedAuthority> auths = new HashSet<>();
        for (String role : roles) {
            auths.add(new SimpleGrantedAuthority(role));
        }
        return auths;
    }
    
    public Long getId() {
        return customerId;
    }
    
}
