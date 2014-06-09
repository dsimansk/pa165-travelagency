package cz.muni.fi.pa165.travelagency.data.dao;

import cz.muni.fi.pa165.travelagency.data.entity.Customer;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martin Gerlasinsky
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testContext.xml"})
@Transactional
public class CustomerDAOTest {

    @Autowired
    @Qualifier(value="customerDAO")
    private CustomerDAO customerDAO;
    
    @Test
    public void createCustomer() {
        Customer customer = prepareCustomer();

        customerDAO.create(customer);

        Customer result = customerDAO.get(customer.getId());

        assertEquals(customer, result);
        assertCustomerDeepEquals(customer, result);

    }
    @Test
    public void createCustomerWithWrongAttributes() {
        try {
            customerDAO.create(null);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        Customer customer = prepareCustomer();
        customer.setId(1l);
        try {
            customerDAO.create(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null name
        customer = prepareCustomer();
        customer.setName(null);
        try {
            customerDAO.create(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //empty name
        customer = prepareCustomer();
        customer.setName("");
        try {
            customerDAO.create(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null adress
        customer = prepareCustomer();
        customer.setAddress(null);
        try {
            customerDAO.create(customer);
        } catch (DataAccessException ex) {
            //OK
        }

        //empty address
        customer = prepareCustomer();
        customer.setAddress("");
        try {
            customerDAO.create(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
        
        //null phone number
        customer = prepareCustomer();
        customer.setPhoneNumber(null);
        try {
            customerDAO.create(customer);
        } catch (DataAccessException ex) {
            //OK
        }

        //empty phone number
        customer = prepareCustomer();
        customer.setPhoneNumber("");
        try {
            customerDAO.create(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
        
    }
    @Test
    public void getCustomer() {

        assertNull(customerDAO.get(1l));

        Customer customer = prepareCustomer();

        customerDAO.create(customer);
        Long customerId = customer.getId();

        Customer result = customerDAO.get(customerId);

        assertEquals(customer, result);
    }
    
    @Test
    public void updateCustomer() {
        Customer customer1 = prepareCustomer();
        Customer customer2 = prepareCustomer();
        customer2.setName("another entry");

        customerDAO.create(customer1);
        customerDAO.create(customer2);
        Long customer1Id = customer1.getId();

        //name test
        customer1.setName("updated name");
        customerDAO.update(customer1);
        Customer result = customerDAO.get(customer1Id);
        assertCustomerDeepEquals(customer1, result);

        //address test
        customer1.setAddress("Updated address, city");
        customerDAO.update(customer1);
        result = customerDAO.get(customer1Id);
        assertCustomerDeepEquals(customer1, result);

        //phone number test
        customer1.setPhoneNumber("+420 775 654 321");
        customerDAO.update(customer1);
        result = customerDAO.get(customer1Id);
        assertCustomerDeepEquals(customer1, result);
    }
    
    @Test
    public void updateCustomerWithWronAttributes() {
        //null argument
        try {
            customerDAO.update(null);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
        //null id exception
        Customer customer = prepareCustomer();
        try {
            customerDAO.update(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
        //not found entity
        customer.setId(1l);
        try {
            customerDAO.update(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
        //null name
        customer = prepareCustomer();
        customerDAO.create(customer);
        Long customerId = customer.getId();
        customer.setName(null);
        try {
            customerDAO.update(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //empty destination
        customer = prepareCustomer();
        customer.setId(customerId);
        customer.setName("");
        try {
            customerDAO.update(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null adress
        customer = prepareCustomer();
        customer.setId(customerId);
        customer.setAddress(null);
        try {
            customerDAO.update(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //empty address
        customer = prepareCustomer();
        customer.setId(customerId);
        customer.setAddress("");
        try {
            customerDAO.update(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
        
        //null phone number
        customer = prepareCustomer();
        customer.setId(customerId);
        customer.setPhoneNumber(null);
        try {
            customerDAO.update(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //empty phone number
        customer = prepareCustomer();
        customer.setId(customerId);
        customer.setPhoneNumber("");
        try {
            customerDAO.update(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }


    }

    @Test
    public void deleteCustomer() {
        Customer customer1 = prepareCustomer();
        Customer customer2 = prepareCustomer();
        customer2.setName("another entry");

        customerDAO.create(customer1);
        customerDAO.create(customer2);

        assertNotNull(customerDAO.get(customer1.getId()));
        assertNotNull(customerDAO.get(customer2.getId()));

        customerDAO.delete(customer2);

        assertNotNull(customerDAO.get(customer1.getId()));
        assertNull(customerDAO.get(customer2.getId()));
    }

    @Test
    public void deleteCustomerWithWrongAttributes() {
        //null argument
        try {
            customerDAO.delete(null);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
        //null id exception
        Customer customer = prepareCustomer();
        try {
            customerDAO.delete(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
        customer = prepareCustomer();
        customer.setId(1l);
        try {
            customerDAO.delete(customer);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
    }
    

    private static Customer prepareCustomer() {
        Customer customer = new Customer();
        customer.setName("John Smith");
        customer.setAddress("Street no.1, City, 12345");
        customer.setPhoneNumber("+420 775 123 456");
        return customer;
    }

    private static void assertCustomerDeepEquals(Customer expected, Customer actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }
}
