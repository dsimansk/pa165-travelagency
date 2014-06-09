package cz.muni.fi.pa165.travelagencyclient.web.validator;

import cz.muni.fi.pa165.travelagencyclient.web.dto.CustomerDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author David Simansky <d.simansky@gmail.com>
 */
@Component("customerValidator")
public class CustomerValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "customer.empty.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "customer.empty.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "customer.empty.phoneNo");
    }
    
}
