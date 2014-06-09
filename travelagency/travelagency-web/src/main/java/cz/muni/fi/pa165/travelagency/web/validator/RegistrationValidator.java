package cz.muni.fi.pa165.travelagency.web.validator;

import cz.muni.fi.pa165.travelagency.data.dto.AccountDTO;
import cz.muni.fi.pa165.travelagency.data.dto.RegistrationDTO;
import cz.muni.fi.pa165.travelagency.service.AccountCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Michal Jurc
 */
@Component("registrationValidator")
public class RegistrationValidator implements Validator {
    
    @Autowired
    @Qualifier("accountCRUDService")
    AccountCRUDService accountCRUDService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "customer.empty.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "customer.empty.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "customer.empty.phoneNo");
        
        RegistrationDTO registration = (RegistrationDTO) target;
        
        if (registration.getUsername().length() < 5) {
            errors.rejectValue("username", "registration.short.name");
        }
        
        if (registration.getUsername().length() > 20) {
            errors.rejectValue("username", "registration.long.name");
        }
        
        if (registration.getUsername().contains(" ")) {
            errors.rejectValue("username", "registration.whitespace.name");
        }
        
        if (registration.getPassword().length() < 5) {
            errors.rejectValue("password", "registration.short.password");
        }
        
        if (registration.getPassword().length() > 20) {
            errors.rejectValue("password", "registration.long.password");
        }
        
        if (registration.getPassword().contains(" ")) {
            errors.rejectValue("password", "registration.whitespace.password");
        }
        
        AccountDTO account = new AccountDTO();
        account.setName(registration.getUsername());
        
        try {
            accountCRUDService.validateDuplicity(account);
        } catch (IllegalArgumentException ex) {
            errors.rejectValue("username", "registration.inuse.username");
        }
    }
    
}
