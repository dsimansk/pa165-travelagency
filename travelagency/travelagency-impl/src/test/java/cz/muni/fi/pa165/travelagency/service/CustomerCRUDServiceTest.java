package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dao.impl.CustomerDAOImpl;
import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Customer;
import cz.muni.fi.pa165.travelagency.service.impl.CustomerCRUDServiceImpl;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Michal Jurc
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerCRUDServiceTest {

    @Mock
    private CustomerDAOImpl customerDAO;
    @InjectMocks
    private CustomerCRUDServiceImpl customerCRUDService;

    @Test
    public void createCustomer() {
        CustomerDTO customerDTO = prepareCustomerDTO();
        Customer customer = prepareCustomerEntity();
        customerCRUDService.create(customerDTO);

        verify(customerDAO).create(customer);
        verify(customerDAO, times(0)).get(any(Long.class));
        verify(customerDAO, times(0)).update(any(Customer.class));
        verify(customerDAO, times(0)).delete(any(Customer.class));
        verify(customerDAO, times(0)).getAll();
    }

    @Test
    public void createCustomerExceptions() {
        CustomerDTO customerDTO = null;
        try {
            customerCRUDService.create(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        customerDTO = prepareCustomerDTO();
        customerDTO.setId(1l);
        try {
            customerCRUDService.create(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void customerValidationExceptions() {
        CustomerDTO customerDTO = prepareCustomerDTO();

        customerDTO.setName(null);
        try {
            customerCRUDService.create(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        customerDTO.setName("");
        try {
            customerCRUDService.create(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        customerDTO.setName("John Smith");
        customerDTO.setAddress(null);
        try {
            customerCRUDService.create(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        customerDTO.setAddress("");
        try {
            customerCRUDService.create(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        customerDTO.setAddress("Street no.1, City, 12345");
        customerDTO.setPhoneNumber(null);
        try {
            customerCRUDService.create(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        customerDTO.setPhoneNumber("");
        try {
            customerCRUDService.create(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void getCustomer() {
        Customer customer = prepareCustomerEntity();
        customer.setId(1l);
        when(customerDAO.get(1l)).thenReturn(customer);
        CustomerDTO expected = prepareCustomerDTO();
        expected.setId(1l);
        CustomerDTO actual = customerCRUDService.get(1l);

        verify(customerDAO).get(1l);
        verify(customerDAO, times(0)).create(any(Customer.class));
        verify(customerDAO, times(0)).update(any(Customer.class));
        verify(customerDAO, times(0)).delete(any(Customer.class));
        verify(customerDAO, times(0)).getAll();

        assertDeepEquals(expected, actual);
    }

    @Test
    public void getCustomerExceptions() {
        try {
            customerCRUDService.get(null);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void updateCustomer() {
        CustomerDTO customerDTO = prepareCustomerDTO();
        customerDTO.setId(1l);
        Customer customer = prepareCustomerEntity();
        customer.setId(1l);
        customerCRUDService.update(customerDTO);

        verify(customerDAO).update(customer);
        verify(customerDAO, times(0)).create(any(Customer.class));
        verify(customerDAO, times(0)).get(any(Long.class));
        verify(customerDAO, times(0)).delete(any(Customer.class));
        verify(customerDAO, times(0)).getAll();
    }

    @Test
    public void updateCustomerExceptions() {
        CustomerDTO customerDTO = null;
        try {
            customerCRUDService.update(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        customerDTO = prepareCustomerDTO();
        try {
            customerCRUDService.update(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void deleteCustomer() {
        CustomerDTO customerDTO = prepareCustomerDTO();
        customerDTO.setId(1l);
        Customer customer = prepareCustomerEntity();
        customer.setId(1l);
        customerCRUDService.delete(customerDTO);

        verify(customerDAO).delete(customer);
        verify(customerDAO, times(0)).create(any(Customer.class));
        verify(customerDAO, times(0)).get(any(Long.class));
        verify(customerDAO, times(0)).update(any(Customer.class));
        verify(customerDAO, times(0)).getAll();
    }

    @Test
    public void deleteCustomerExceptions() {
        CustomerDTO customerDTO = null;
        try {
            customerCRUDService.delete(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        customerDTO = prepareCustomerDTO();
        try {
            customerCRUDService.delete(customerDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = prepareCustomerEntity();
        customer1.setId(1l);
        customers.add(customer1);
        Customer customer2 = prepareCustomerEntity();
        customer2.setId(2l);
        customers.add(customer2);
        Customer customer3 = prepareCustomerEntity();
        customer3.setId(3l);
        customers.add(customer3);
        when(customerDAO.getAll()).thenReturn(customers);
        List<CustomerDTO> expected = new ArrayList<>();
        CustomerDTO customerDTO1 = prepareCustomerDTO();
        customerDTO1.setId(1l);
        expected.add(customerDTO1);
        CustomerDTO customerDTO2 = prepareCustomerDTO();
        customerDTO2.setId(2l);
        expected.add(customerDTO2);
        CustomerDTO customerDTO3 = prepareCustomerDTO();
        customerDTO3.setId(3l);
        expected.add(customerDTO3);

        List<CustomerDTO> actual = customerCRUDService.getAll();

        verify(customerDAO).getAll();
        verify(customerDAO, times(0)).create(any(Customer.class));
        verify(customerDAO, times(0)).get(any(Long.class));
        verify(customerDAO, times(0)).update(any(Customer.class));
        verify(customerDAO, times(0)).delete(any(Customer.class));

        assertDeepEquals(expected, actual);
    }

    private CustomerDTO prepareCustomerDTO() {
        CustomerDTO customer = new CustomerDTO();
        customer.setName("John Smith");
        customer.setAddress("Street no.1, City, 12345");
        customer.setPhoneNumber("+420 775 123 456");
        return customer;
    }

    private Customer prepareCustomerEntity() {
        Customer customer = new Customer();
        customer.setName("John Smith");
        customer.setAddress("Street no.1, City, 12345");
        customer.setPhoneNumber("+420 775 123 456");
        return customer;
    }

    private void assertDeepEquals(CustomerDTO expected, CustomerDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }

    private void assertDeepEquals(List<CustomerDTO> expectedList, List<CustomerDTO> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            CustomerDTO expected = expectedList.get(i);
            CustomerDTO actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
}
