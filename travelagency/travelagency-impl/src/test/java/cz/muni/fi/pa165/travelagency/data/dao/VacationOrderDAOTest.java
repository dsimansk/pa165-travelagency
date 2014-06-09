package cz.muni.fi.pa165.travelagency.data.dao;

import cz.muni.fi.pa165.travelagency.data.entity.Customer;
import cz.muni.fi.pa165.travelagency.data.entity.Vacation;
import cz.muni.fi.pa165.travelagency.data.entity.VacationOrder;
import static cz.muni.fi.pa165.travelagency.util.Util.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
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
 * @author Michal Jurc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
@Transactional
public class VacationOrderDAOTest {

    @Autowired
    @Qualifier(value = "customerDAO")
    private CustomerDAO customerDAO;
    @Autowired
    @Qualifier(value = "vacationDAO")
    private VacationDAO vacationDAO;
    @Autowired
    @Qualifier(value = "vacationOrderDAO")
    private VacationOrderDAO vacationOrderDAO;
    private Customer customer;
    private Vacation vacation;

    @Before
    public void setUp() {
        customer = prepareCustomer();
        customerDAO.create(customer);

        vacation = prepareVacation();
        vacationDAO.create(vacation);
    }

    @Test
    public void createVacation() {
        VacationOrder actual = new VacationOrder();
        VacationOrder expected = new VacationOrder();
        actual.setCustomer(customer);
        actual.setVacation(vacation);
        expected.setCustomer(customer);
        expected.setVacation(vacation);
        vacationOrderDAO.create(actual);
        expected.setId(actual.getId());
        assertDeepEquals(expected, actual);
    }

    @Test
    public void createVacationExceptions() {

        try {
            vacationOrderDAO.create(null);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        VacationOrder order = new VacationOrder();
        order.setId(1l);
        try {
            vacationOrderDAO.create(order);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        order.setId(null);
        order.setReservations(0);
        try {
            vacationOrderDAO.create(order);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void vacationValidationExceptions() {

        VacationOrder order = new VacationOrder();
        order.setCustomer(null);
        order.setVacation(vacation);
        try {
            vacationOrderDAO.create(order);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        order.setCustomer(customer);
        order.setVacation(null);
        try {
            vacationOrderDAO.create(order);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void getVacation() {

        assertNull(vacationOrderDAO.get(1l));

        VacationOrder order = new VacationOrder();
        order.setCustomer(customer);
        order.setVacation(vacation);
        VacationOrder expected = new VacationOrder();
        expected.setCustomer(customer);
        expected.setVacation(vacation);
        vacationOrderDAO.create(order);
        Long id = order.getId();
        expected.setId(id);
        VacationOrder actual = vacationOrderDAO.get(id);
        assertDeepEquals(expected, actual);
    }

    @Test
    public void getVacationExceptions() {

        try {
            vacationOrderDAO.get(null);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void updateVacation() {

        Customer customer2 = prepareCustomer();
        customer.setName("John Doe");
        customerDAO.create(customer2);

        VacationOrder actual = new VacationOrder();
        actual.setCustomer(customer);
        actual.setVacation(vacation);

        VacationOrder expected = new VacationOrder();
        expected.setCustomer(customer2);
        expected.setVacation(vacation);

        vacationOrderDAO.create(actual);
        expected.setId(actual.getId());
        actual.setCustomer(customer2);
        vacationOrderDAO.update(actual);
        assertDeepEquals(expected, actual);
    }

    @Test
    public void updateVacationExceptions() {

        VacationOrder order = new VacationOrder();
        try {
            vacationOrderDAO.update(null);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
        try {
            vacationOrderDAO.update(order);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
        vacation.setId(1l);
        try {
            vacationOrderDAO.update(order);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void deleteVacation() {

        Customer customer2 = prepareCustomer();
        customer.setName("John Doe");
        customerDAO.create(customer2);

        VacationOrder order = new VacationOrder();
        order.setCustomer(customer);
        order.setVacation(vacation);

        VacationOrder another = new VacationOrder();
        another.setCustomer(customer2);
        another.setVacation(vacation);

        vacationOrderDAO.create(order);
        vacationOrderDAO.create(another);
        Long id = vacation.getId();
        vacationOrderDAO.delete(order);
        assertNull(vacationOrderDAO.get(id));
        assertNotNull(vacationOrderDAO.get(another.getId()));
    }

    @Test
    public void deleteVacationExceptions() {

        VacationOrder order = new VacationOrder();
        try {
            vacationOrderDAO.delete(null);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
        try {
            vacationOrderDAO.delete(order);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
        order.setId(1l);
        try {
            vacationOrderDAO.delete(order);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void getAllVacations() {

        Customer customer2 = prepareCustomer();
        customer.setName("John Doe");
        customerDAO.create(customer2);

        VacationOrder actual1 = new VacationOrder();
        actual1.setCustomer(customer);
        actual1.setVacation(vacation);

        VacationOrder actual2 = new VacationOrder();
        actual2.setCustomer(customer2);
        actual2.setVacation(vacation);
        vacationOrderDAO.create(actual1);
        vacationOrderDAO.create(actual2);
        List<VacationOrder> expected = new ArrayList<>();
        VacationOrder expected1 = new VacationOrder();
        expected1.setCustomer(customer);
        expected1.setVacation(vacation);
        expected1.setId(actual1.getId());
        expected.add(expected1);
        VacationOrder expected2 = new VacationOrder();
        expected2.setCustomer(customer2);
        expected2.setVacation(vacation);
        expected2.setId(actual2.getId());
        expected.add(expected2);
        List<VacationOrder> actual = vacationOrderDAO.getAll();
        assertDeepEquals(expected, actual);
    }

    @Test
    public void getOrdersByCustomer() {

        Customer customer2 = prepareCustomer();
        customer.setName("John Doe");
        customerDAO.create(customer2);

        Vacation vacation2 = prepareVacation();
        vacation2.setDestination("Praha");
        vacationDAO.create(vacation2);

        VacationOrder actual1 = new VacationOrder();
        actual1.setCustomer(customer);
        actual1.setVacation(vacation);

        VacationOrder actual2 = new VacationOrder();
        actual2.setCustomer(customer);
        actual2.setVacation(vacation2);

        VacationOrder order = new VacationOrder();
        order.setCustomer(customer2);
        order.setVacation(vacation);

        vacationOrderDAO.create(actual1);
        vacationOrderDAO.create(actual2);
        vacationOrderDAO.create(order);
        List<VacationOrder> expected = new ArrayList<>();
        VacationOrder expected1 = new VacationOrder();
        expected1.setCustomer(customer);
        expected1.setVacation(vacation);
        expected1.setId(actual1.getId());
        expected.add(expected1);
        VacationOrder expected2 = new VacationOrder();
        expected2.setCustomer(customer);
        expected2.setVacation(vacation2);
        expected2.setId(actual2.getId());
        expected.add(expected2);
        List<VacationOrder> actual = vacationOrderDAO.getOrdersByCustomer(customer);
        assertDeepEquals(expected, actual);
    }

    private static Customer prepareCustomer() {
        Customer customer = new Customer();
        customer.setName("John Smith");
        customer.setAddress("Street no.1, City, 12345");
        customer.setPhoneNumber("+420 775 123 456");
        return customer;
    }

    private Vacation prepareVacation() {
        Vacation preparedVacation = new Vacation();
        preparedVacation.setDestination("Brno");
        preparedVacation.setStartDate(newDate(2012, 10, 14));
        preparedVacation.setEndDate(newDate(2012, 10, 15));
        preparedVacation.setPrice(new BigDecimal(2000));
        preparedVacation.setMaxCapacity(1);
        return preparedVacation;
    }

    private void assertDeepEquals(VacationOrder expected, VacationOrder actual) {
        assertEquals(expected.getCustomer(), actual.getCustomer());
        assertEquals(expected.getVacation(), actual.getVacation());
    }

    private void assertDeepEquals(List<VacationOrder> expectedList, List<VacationOrder> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            VacationOrder expected = expectedList.get(i);
            VacationOrder actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
}
