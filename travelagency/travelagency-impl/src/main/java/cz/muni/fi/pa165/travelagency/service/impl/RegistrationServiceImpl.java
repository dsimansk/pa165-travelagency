package cz.muni.fi.pa165.travelagency.service.impl;

import cz.muni.fi.pa165.travelagency.data.dto.AccountDTO;
import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.data.dto.RegistrationDTO;
import cz.muni.fi.pa165.travelagency.service.AccountCRUDService;
import cz.muni.fi.pa165.travelagency.service.CustomerCRUDService;
import cz.muni.fi.pa165.travelagency.service.RegistrationService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Michal Jurc
 */
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {
    
    @Autowired
    @Qualifier("customerCRUDService")
    CustomerCRUDService customerCRUDService;
    
    @Autowired
    @Qualifier("accountCRUDService")
    AccountCRUDService accountCRUDService;

    @Override
    public void registerAccount(RegistrationDTO registration) {
        CustomerDTO customer = new CustomerDTO();
        customer.setName(registration.getName());
        customer.setAddress(registration.getAddress());
        customer.setPhoneNumber(registration.getPhoneNumber());
        customerCRUDService.create(customer);
        AccountDTO account = new AccountDTO();
        account.setName(registration.getUsername());
        account.setPassword(registration.getPassword());
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_CUSTOMER");
        account.setRoles(roles);
        account.setEnabled(true);
        account.setCustomerId(customer.getId());
        accountCRUDService.create(account);
    }
    
}
