package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dao.impl.TourDAOImpl;
import cz.muni.fi.pa165.travelagency.data.dao.impl.VacationDAOImpl;
import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Vacation;
import cz.muni.fi.pa165.travelagency.service.impl.VacationCRUDServiceImpl;
import static cz.muni.fi.pa165.travelagency.util.Util.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Michal Jurc
 */
@RunWith(MockitoJUnitRunner.class)
public class VacationCRUDServiceTest {
    
    @Mock
    private VacationDAOImpl vacationDAO;
    @Mock
    private TourDAOImpl tourDAO;
    @InjectMocks
    private VacationCRUDServiceImpl vacationCRUDService;
    
    @Test
    public void createVacation() {
        VacationDTO vacationDTO = prepareVacationDTO();
        Vacation vacation = prepareVacationEntity();
        vacationCRUDService.create(vacationDTO);
        
        verify(vacationDAO).create(vacation);
        verify(vacationDAO, times(0)).get(any(Long.class));
        verify(vacationDAO, times(0)).update(any(Vacation.class));
        verify(vacationDAO, times(0)).delete(any(Vacation.class));
        verify(vacationDAO, times(0)).getAll();
    }
    
    @Test
    public void createCustomerExceptions() {
        VacationDTO vacationDTO = null;
        try {
            vacationCRUDService.create(vacationDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacationDTO = prepareVacationDTO();
        vacationDTO.setId(1l);
        try {
            vacationCRUDService.create(vacationDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }
    
    @Test
    public void vacationValidationExceptions() {
        VacationDTO vacation = prepareVacationDTO();
        vacation.setDestination(null);
        try {
            vacationCRUDService.create(vacation);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacation = prepareVacationDTO();
        vacation.setDestination("");
        try {
            vacationCRUDService.create(vacation);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacation = prepareVacationDTO();
        vacation.setStartDate(null);
        try {
            vacationCRUDService.create(vacation);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacation = prepareVacationDTO();
        vacation.setEndDate(null);
        try {
            vacationCRUDService.create(vacation);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacation = prepareVacationDTO();
        vacation.setEndDate(newDate(2012,10,14));
        vacation.setStartDate(newDate(2012,10,15));
        try {
            vacationCRUDService.create(vacation);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacation = prepareVacationDTO();
        vacation.setPrice(null);
        try {
            vacationCRUDService.create(vacation);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacation = prepareVacationDTO();
        vacation.setMaxCapacity(0);
        try {
            vacationCRUDService.create(vacation);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }
    
    @Test
    public void getVacation() {
        VacationDTO expected = prepareVacationDTO();
        expected.setId(1l);
        Vacation vacation = prepareVacationEntity();
        vacation.setId(1l);
        when(vacationDAO.get(1l)).thenReturn(vacation);
        VacationDTO actual = vacationCRUDService.get(1l);
        
        verify(vacationDAO).get(1l);
        verify(vacationDAO, times(0)).create(any(Vacation.class));
        verify(vacationDAO, times(0)).update(any(Vacation.class));
        verify(vacationDAO, times(0)).delete(any(Vacation.class));
        verify(vacationDAO, times(0)).getAll();
        
        assertDeepEquals(expected, actual);
    }
    
    @Test
    public void getVacationExceptions() {
        try {
            vacationCRUDService.get(null);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }
    
    @Test
    public void updateVacation() {
        VacationDTO vacationDTO = prepareVacationDTO();
        vacationDTO.setId(1l);
        Vacation vacation = prepareVacationEntity();
        vacation.setId(1l);
        vacationCRUDService.update(vacationDTO);
        
        verify(vacationDAO).update(vacation);
        verify(vacationDAO, times(0)).create(any(Vacation.class));
        verify(vacationDAO, times(0)).get(any(Long.class));
        verify(vacationDAO, times(0)).delete(any(Vacation.class));
        verify(vacationDAO, times(0)).getAll();
    }
    
    @Test
    public void updateVacationExceptions() {
        VacationDTO vacationDTO = null;
        try {
            vacationCRUDService.update(vacationDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacationDTO = prepareVacationDTO();
        vacationDTO.setId(null);
        try {
            vacationCRUDService.update(vacationDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }
    
    @Test
    public void deleteVacation() {
        VacationDTO vacationDTO = prepareVacationDTO();
        vacationDTO.setId(1l);
        Vacation vacation = prepareVacationEntity();
        vacation.setId(1l);
        vacationCRUDService.delete(vacationDTO);
        
        verify(vacationDAO).delete(vacation);
        verify(vacationDAO, times(0)).create(any(Vacation.class));
        verify(vacationDAO, times(0)).get(any(Long.class));
        verify(vacationDAO, times(0)).update(any(Vacation.class));
        verify(vacationDAO, times(0)).getAll();
    }
    
    @Test
    public void deleteVacationExceptions() {
        VacationDTO vacationDTO = null;
        try {
            vacationCRUDService.delete(vacationDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        vacationDTO = prepareVacationDTO();
        vacationDTO.setId(null);
        try {
            vacationCRUDService.delete(vacationDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }
    
    @Test
    public void getAllVacationOrders() {
        List<Vacation> vacations = new ArrayList<>();
        Vacation vacation1 = prepareVacationEntity();
        vacation1.setId(1l);
        vacations.add(vacation1);
        Vacation vacation2 = prepareVacationEntity();
        vacation2.setId(2l);
        vacations.add(vacation2);
        Vacation vacation3 = prepareVacationEntity();
        vacation3.setId(3l);
        vacations.add(vacation3);
        when(vacationDAO.getAll()).thenReturn(vacations);
        List<VacationDTO> expected = new ArrayList<>();
        VacationDTO customerDTO1 = prepareVacationDTO();
        customerDTO1.setId(1l);
        expected.add(customerDTO1);
        VacationDTO customerDTO2 = prepareVacationDTO();
        customerDTO2.setId(2l);
        expected.add(customerDTO2);
        VacationDTO customerDTO3 = prepareVacationDTO();
        customerDTO3.setId(3l);
        expected.add(customerDTO3);
        
        List<VacationDTO> actual = vacationCRUDService.getAll();
        
        verify(vacationDAO).getAll();
        verify(vacationDAO, times(0)).create(any(Vacation.class));
        verify(vacationDAO, times(0)).get(any(Long.class));
        verify(vacationDAO, times(0)).update(any(Vacation.class));
        verify(vacationDAO, times(0)).delete(any(Vacation.class));
        
        assertDeepEquals(expected, actual);
    }
    
    private VacationDTO prepareVacationDTO() {
        VacationDTO vacation = new VacationDTO();
        vacation.setDestination("Brno");
        vacation.setStartDate(newDate(2012,10,14));
        vacation.setEndDate(newDate(2012,10,15));
        vacation.setPrice(new BigDecimal(2000));
        vacation.setMaxCapacity(1);
        return vacation;
    }
    
    private Vacation prepareVacationEntity() {
        Vacation vacation = new Vacation();
        vacation.setDestination("Brno");
        vacation.setStartDate(newDate(2012,10,14));
        vacation.setEndDate(newDate(2012,10,15));
        vacation.setPrice(new BigDecimal(2000));
        vacation.setMaxCapacity(1);
        return vacation;
    }
    
    private void assertDeepEquals(VacationDTO expected, VacationDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDestination(), actual.getDestination());
        //assertEquals(expected.getStartDate(), actual.getStartDate());
        //assertEquals(expected.getEndDate(), actual.getEndDate());
        //assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getMaxCapacity(), actual.getMaxCapacity());
        assertEquals(expected.getReserved(), actual.getReserved());
        assertEquals(expected.getTours(), actual.getTours());
    }
    
    private void assertDeepEquals(List<VacationDTO> expectedList, List<VacationDTO> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            VacationDTO expected = expectedList.get(i);
            VacationDTO actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
    
}
