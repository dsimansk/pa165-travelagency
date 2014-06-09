package cz.muni.fi.pa165.travelagency.web.validator;

import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Michal Jurc
 */
@Component("tourValidator")
public class TourValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TourDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destination", "tour.empty.destination");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "tour.empty.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "tour.empty.startdate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durationInHours", "tour.empty.duration");
        
        TourDTO tourDTO = (TourDTO) target;
        
        if (tourDTO.getStartDate() == null) {
            errors.rejectValue("startDate", "error.date.wrongformat");
        }
        
        if (tourDTO.getDurationInHours() < 1) {
            errors.rejectValue("durationInHours", "tour.wrongduration");
        }
    }
    
}
