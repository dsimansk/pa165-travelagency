package cz.muni.fi.pa165.travelagency.web.validator;

import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Michal Jurc
 */
@Component("vacationValidator")
public class VacationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return VacationDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destination", "vacation.empty.destination");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "vacation.empty.startdate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "vacation.empty.enddate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "vacation.empty.price");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maxCapacity", "vacation.empty.capacity");
        
        VacationDTO vacationDTO = (VacationDTO) target;
        
        if (vacationDTO.getStartDate() == null) {
            errors.rejectValue("startDate", "vacation.date.wrongformat");
        }
        
        if (vacationDTO.getEndDate() == null) {
            errors.rejectValue("endDate", "vacation.date.wrongformat");
        }
        
        if (vacationDTO.getEndDate() != null && vacationDTO.getStartDate() != null 
                && vacationDTO.getEndDate().compareTo(vacationDTO.getStartDate()) < 0) {
            errors.rejectValue("startDate", "vacation.date.wrongdates");
            errors.rejectValue("endDate", "vacation.date.wrongdates");
        }
        
        if (vacationDTO.getMaxCapacity() < 1) {
            errors.rejectValue("maxCapacity", "vacation.wrongcapacity");
        }
    }
    
}
