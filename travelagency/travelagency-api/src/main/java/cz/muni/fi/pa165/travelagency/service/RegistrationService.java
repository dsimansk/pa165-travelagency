package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dto.RegistrationDTO;

/**
 *
 * @author Michal Jurc
 */
public interface RegistrationService {
    
    public void registerAccount(RegistrationDTO registration);
    
}
