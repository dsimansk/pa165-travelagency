package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dao.impl.TourDAOImpl;
import cz.muni.fi.pa165.travelagency.data.dao.impl.VacationOrderDAOImpl;
import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationOrderDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Customer;
import cz.muni.fi.pa165.travelagency.data.entity.Vacation;
import cz.muni.fi.pa165.travelagency.data.entity.VacationOrder;
import cz.muni.fi.pa165.travelagency.service.impl.VacationCRUDServiceImpl;
import cz.muni.fi.pa165.travelagency.service.impl.VacationOrderCRUDServiceImpl;
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
 * @author Martin Gerlasinsky
 */
@RunWith(MockitoJUnitRunner.class)
public class VacationOrderCRUDServiceTest {

    @Mock
    private VacationOrderDAOImpl vacationOrderDAO;
    @Mock
    private TourDAOImpl tourDAO;
    @Mock
    private VacationCRUDServiceImpl vacationCRUDService;
    
    @InjectMocks
    private VacationOrderCRUDServiceImpl vacationOrderCRUDService;

    @Test
    public void createVacationOrder() {
        VacationOrder order = prepareVacationOrderEntity();
        VacationOrderDTO orderDTO = prepareVacationOrderDTO();
        vacationOrderCRUDService.create(orderDTO);

        verify(vacationOrderDAO).create(order);
        verify(vacationOrderDAO, times(0)).get(any(Long.class));
        verify(vacationOrderDAO, times(0)).update(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).delete(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).getAll();
    }

    @Test
    public void createVacationOrderExceptions() {
        VacationOrderDTO orderDTO = null;
        try {
            vacationOrderCRUDService.create(orderDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        orderDTO = prepareVacationOrderDTO();
        orderDTO.setId(1l);
        try {
            vacationOrderCRUDService.create(orderDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void getVacationOrder() {
        VacationOrder order = prepareVacationOrderEntity();
        order.setId(1l);
        when(vacationOrderDAO.get(1l)).thenReturn(order);
        VacationOrderDTO expected = prepareVacationOrderDTO();
        expected.setId(1l);
        VacationOrderDTO actual = vacationOrderCRUDService.get(1l);

        verify(vacationOrderDAO).get(1l);
        verify(vacationOrderDAO, times(0)).create(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).update(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).delete(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).getAll();

        assertDeepEquals(expected, actual);
    }

    @Test
    public void getVacationOrderExceptions() {
        try {
            vacationOrderCRUDService.get(null);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void vacationOrderValidationExceptions() {


        VacationOrderDTO orderDTO = prepareVacationOrderDTO();
        orderDTO.setCustomer(null);
        orderDTO.setVacation(prepareVacationDTO());
        try {
            vacationOrderCRUDService.create(orderDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        orderDTO.setCustomer(prepareCustomerDTO());
        orderDTO.setVacation(null);
        try {
            vacationOrderCRUDService.create(orderDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void updateVacationOrder() {
        VacationOrderDTO orderDTO = prepareVacationOrderDTO();
        orderDTO.setId(1l);
        VacationOrder order = prepareVacationOrderEntity();
        order.setId(1l);
        vacationOrderCRUDService.update(orderDTO);

        verify(vacationOrderDAO).update(order);
        verify(vacationOrderDAO, times(0)).create(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).get(any(Long.class));
        verify(vacationOrderDAO, times(0)).delete(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).getAll();
    }

    @Test
    public void updateVacationOrderExceptions() {
        VacationOrderDTO order = new VacationOrderDTO();
        try {
            vacationOrderCRUDService.update(null);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        order = prepareVacationOrderDTO();
        try {
            vacationOrderCRUDService.update(order);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void deleteVacationOrder() {
        VacationOrderDTO orderDTO = prepareVacationOrderDTO();
        orderDTO.setId(1l);
        VacationOrder order = prepareVacationOrderEntity();
        order.setId(1l);
        vacationOrderCRUDService.delete(orderDTO);

        verify(vacationOrderDAO).delete(order);
        verify(vacationOrderDAO, times(0)).create(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).get(any(Long.class));
        verify(vacationOrderDAO, times(0)).update(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).getAll();
    }

    @Test
    public void deleteVacationOrderExceptions() {
        VacationOrderDTO order = new VacationOrderDTO();
        try {
            vacationOrderCRUDService.delete(null);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
        order = prepareVacationOrderDTO();
        try {
            vacationOrderCRUDService.delete(order);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }
    
        @Test
    public void getAllVacations() {
        List<VacationOrder> vacationOrders = new ArrayList<>();
        VacationOrder vacation1 = prepareVacationOrderEntity();
        vacation1.setId(1l);
        vacationOrders.add(vacation1);
        VacationOrder vacation2 = prepareVacationOrderEntity();
        vacation2.setId(2l);
        vacationOrders.add(vacation2);
        VacationOrder vacation3 = prepareVacationOrderEntity();
        vacation3.setId(3l);
        vacationOrders.add(vacation3);
        when(vacationOrderDAO.getAll()).thenReturn(vacationOrders);
        List<VacationOrderDTO> expected = new ArrayList<>();
        VacationOrderDTO customerDTO1 = prepareVacationOrderDTO();
        customerDTO1.setId(1l);
        expected.add(customerDTO1);
        VacationOrderDTO customerDTO2 = prepareVacationOrderDTO();
        customerDTO2.setId(2l);
        expected.add(customerDTO2);
        VacationOrderDTO customerDTO3 = prepareVacationOrderDTO();
        customerDTO3.setId(3l);
        expected.add(customerDTO3);
        
        List<VacationOrderDTO> actual = vacationOrderCRUDService.getAll();
        
        verify(vacationOrderDAO).getAll();
        verify(vacationOrderDAO, times(0)).create(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).get(any(Long.class));
        verify(vacationOrderDAO, times(0)).update(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).delete(any(VacationOrder.class));
        
        assertDeepEquals(expected, actual);
    }
        
            
    @Test
    public void getOrdersByCustomer() {
        Customer customer = prepareCustomerEntity();
        customer.setId(1l);
        VacationOrder order = prepareVacationOrderEntity();
        order.setCustomer(customer);
        List<VacationOrder> orders = new ArrayList<>();
        orders.add(order);
        when(vacationOrderDAO.getOrdersByCustomer(customer)).thenReturn(orders);
        CustomerDTO customerDTO = prepareCustomerDTO();
        customerDTO.setId(1l);
        VacationOrderDTO orderDTO = prepareVacationOrderDTO();
        orderDTO.setCustomer(customerDTO);
        List<VacationOrderDTO> expected = new ArrayList<>();
        expected.add(orderDTO);
        List<VacationOrderDTO> actual = vacationOrderCRUDService.getOrdersByCustomer(customerDTO.getId());
        
        verify(vacationOrderDAO).getOrdersByCustomer(customer);
        verify(vacationOrderDAO, times(0)).create(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).get(any(Long.class));
        verify(vacationOrderDAO, times(0)).update(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).delete(any(VacationOrder.class));
        verify(vacationOrderDAO, times(0)).getAll();
        
        assertDeepEquals(expected, actual);
        
    }

    private Customer prepareCustomerEntity() {
        Customer vacationOrder = new Customer();
        vacationOrder.setName("John Smith");
        vacationOrder.setAddress("Street no.1, City, 12345");
        vacationOrder.setPhoneNumber("+420 775 123 456");
        return vacationOrder;
    }

    private Vacation prepareVacationEntity() {
        Vacation preparedVacation = new Vacation();
        preparedVacation.setDestination("Brno");
        preparedVacation.setStartDate(newDate(2012, 10, 14));
        preparedVacation.setEndDate(newDate(2012, 10, 15));
        preparedVacation.setPrice(new BigDecimal(2000));
        preparedVacation.setMaxCapacity(1);
        return preparedVacation;
    }

    private CustomerDTO prepareCustomerDTO() {
        CustomerDTO vacationOrder = new CustomerDTO();
        vacationOrder.setName("John Smith");
        vacationOrder.setAddress("Street no.1, City, 12345");
        vacationOrder.setPhoneNumber("+420 775 123 456");
        return vacationOrder;
    }

    private VacationDTO prepareVacationDTO() {
        VacationDTO preparedVacation = new VacationDTO();
        preparedVacation.setDestination("Brno");
        preparedVacation.setStartDate(newDate(2012, 10, 14));
        preparedVacation.setEndDate(newDate(2012, 10, 15));
        preparedVacation.setPrice(new BigDecimal(2000));
        preparedVacation.setMaxCapacity(1);
        return preparedVacation;
    }

    private VacationOrder prepareVacationOrderEntity() {
        VacationOrder order = new VacationOrder();
        order.setCustomer(prepareCustomerEntity());
        order.setVacation(prepareVacationEntity());
        return order;
    }

    private VacationOrderDTO prepareVacationOrderDTO() {
        VacationOrderDTO order = new VacationOrderDTO();
        order.setCustomer(prepareCustomerDTO());
        order.setVacation(prepareVacationDTO());
        return order;
    }

    private void assertDeepEquals(VacationOrderDTO expected, VacationOrderDTO actual) {
        assertEquals(expected.getCustomer(), actual.getCustomer());
        assertEquals(expected.getVacation(), actual.getVacation());
    }

    private void assertDeepEquals(List<VacationOrderDTO> expectedList, List<VacationOrderDTO> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            VacationOrderDTO expected = expectedList.get(i);
            VacationOrderDTO actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
}
