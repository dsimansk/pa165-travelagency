package cz.muni.fi.pa165.travelagency.data.dao;

import cz.muni.fi.pa165.travelagency.data.entity.Vacation;
import static cz.muni.fi.pa165.travelagency.util.Util.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
 * @author Sebastian Kunec
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
@Transactional
public class VacationDAOTest {

    @Autowired
    @Qualifier(value = "vacationDAO")
    private VacationDAO vacationDAO;

    @Test
    public void createVacation() {
        Vacation actual = prepareVacation();
        Vacation expected = prepareVacation();
        vacationDAO.create(actual);
        expected.setId(actual.getId());
        assertDeepEquals(expected, actual);
    }

    @Test
    public void createVacationExceptions() {
        try {
            vacationDAO.create(null);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        Vacation vacation = prepareVacation();
        vacation.setId(1l);
        try {
            vacationDAO.create(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void vacationValidationExceptions() {
        Vacation vacation = prepareVacation();
        vacation.setDestination(null);
        try {
            vacationDAO.create(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        vacation = prepareVacation();
        vacation.setDestination("");
        try {
            vacationDAO.create(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        vacation = prepareVacation();
        vacation.setStartDate(null);
        try {
            vacationDAO.create(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        vacation = prepareVacation();
        vacation.setEndDate(null);
        try {
            vacationDAO.create(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        vacation = prepareVacation();
        vacation.setEndDate(newDate(2012, 10, 14));
        vacation.setStartDate(newDate(2012, 10, 15));
        try {
            vacationDAO.create(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        vacation = prepareVacation();
        vacation.setPrice(null);
        try {
            vacationDAO.create(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }

        vacation = prepareVacation();
        vacation.setMaxCapacity(0);
        try {
            vacationDAO.create(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void getVacation() {
        assertNull(vacationDAO.get(1l));

        Vacation vacation = prepareVacation();
        Vacation expected = prepareVacation();
        vacationDAO.create(vacation);
        Long id = vacation.getId();
        expected.setId(id);
        Vacation actual = vacationDAO.get(id);
        assertDeepEquals(expected, actual);
    }

    @Test
    public void getVacationExceptions() {
        try {
            vacationDAO.get(null);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void updateVacation() {
        Vacation actual = prepareVacation();
        Vacation expected = prepareVacation();
        vacationDAO.create(actual);
        expected.setId(actual.getId());
        actual.setDestination("Praha");
        vacationDAO.update(actual);
        expected.setDestination("Praha");
        assertDeepEquals(expected, actual);
    }

    @Test
    public void updateVacationExceptions() {
        Vacation vacation = prepareVacation();
        try {
            vacationDAO.update(null);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
        try {
            vacationDAO.update(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
        vacation.setId(1l);
        try {
            vacationDAO.update(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void deleteVacation() {
        Vacation vacation = prepareVacation();
        Vacation another = prepareVacation();
        another.setDestination("Another");
        vacationDAO.create(vacation);
        vacationDAO.create(another);
        Long id = vacation.getId();
        vacationDAO.delete(vacation);
        assertNull(vacationDAO.get(id));
        assertNotNull(vacationDAO.get(another.getId()));
    }

    @Test
    public void deleteVacationExceptions() {
        Vacation vacation = prepareVacation();
        try {
            vacationDAO.delete(null);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
        try {
            vacationDAO.delete(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
        vacation.setId(1l);
        try {
            vacationDAO.delete(vacation);
            fail();
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void getAllVacations() {
        Vacation actual1 = prepareVacation();
        Vacation actual2 = prepareVacation();
        actual2.setDestination("Praha");
        vacationDAO.create(actual1);
        vacationDAO.create(actual2);
        List<Vacation> expected = new ArrayList<>();
        Vacation expected1 = prepareVacation();
        expected1.setId(actual1.getId());
        expected.add(expected1);
        Vacation expected2 = prepareVacation();
        expected2.setDestination("Praha");
        expected2.setId(actual2.getId());
        expected.add(expected2);
        List<Vacation> actual = vacationDAO.getAll();
        assertDeepEquals(expected, actual);
    }

    private Vacation prepareVacation() {
        Vacation vacation = new Vacation();
        vacation.setDestination("Brno");
        vacation.setStartDate(newDate(2012, 10, 14));
        vacation.setEndDate(newDate(2012, 10, 15));
        vacation.setPrice(new BigDecimal(2000));
        vacation.setMaxCapacity(1);
        return vacation;
    }

    private void assertDeepEquals(Vacation expected, Vacation actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDestination(), actual.getDestination());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getMaxCapacity(), actual.getMaxCapacity());
        assertEquals(expected.getReserved(), actual.getReserved());
        assertEquals(expected.getTours(), actual.getTours());
    }

    private void assertDeepEquals(List<Vacation> expectedList, List<Vacation> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Vacation expected = expectedList.get(i);
            Vacation actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
}
