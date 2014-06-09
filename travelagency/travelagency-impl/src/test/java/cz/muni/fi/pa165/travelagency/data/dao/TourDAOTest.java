package cz.muni.fi.pa165.travelagency.data.dao;

import cz.muni.fi.pa165.travelagency.data.entity.Tour;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
 * @author David Simansky
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
@Transactional
public class TourDAOTest {

    @Autowired
    @Qualifier(value = "tourDAO")
    private TourDAO tourDAO;

    @Test
    public void createTour() {
        Tour tour = prepareTour();

        tourDAO.create(tour);

        Tour result = tourDAO.get(tour.getId());

        assertEquals(tour, result);
        assertTourDeepEquals(tour, result);
    }

    @Test
    public void createTourWithWrongAttributes() {
        try {
            tourDAO.create(null);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        Tour tour = prepareTour();
        tour.setId(1l);
        try {
            tourDAO.create(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null destination
        tour = prepareTour();
        tour.setDestination(null);
        try {
            tourDAO.create(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //empty destination
        tour = prepareTour();
        tour.setDestination("");
        try {
            tourDAO.create(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null start date
        tour = prepareTour();
        tour.setStartDate(null);
        try {
            tourDAO.create(tour);
        } catch (DataAccessException ex) {
            //OK
        }

        //less than 0 duration
        tour = prepareTour();
        tour.setDurationInHours(0);
        try {
            tourDAO.create(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
    }

    @Test
    public void getTour() {

        assertNull(tourDAO.get(1l));

        Tour tour = prepareTour();

        tourDAO.create(tour);
        Long tourId = tour.getId();

        Tour result = tourDAO.get(tourId);

        assertEquals(tour, result);
    }

    @Test
    public void updateTour() {
        Tour tour1 = prepareTour();
        Tour tour2 = prepareTour();
        tour2.setDescription("another entry");

        tourDAO.create(tour1);
        tourDAO.create(tour2);
        Long tour1Id = tour1.getId();

        //description test
        tour1.setDescription("updated description");
        tourDAO.update(tour1);
        Tour result = tourDAO.get(tour1Id);
        assertTourDeepEquals(tour1, result);

        //destination test\tour1.setDescription("updated description");
        tour1.setDestination("updated destination");
        tourDAO.update(tour1);
        result = tourDAO.get(tour1Id);
        assertTourDeepEquals(tour1, result);

        //duration test
        tour1.setDurationInHours(10);
        tourDAO.update(tour1);
        result = tourDAO.get(tour1Id);
        assertTourDeepEquals(tour1, result);

        //start date test
        Calendar cal = new GregorianCalendar();
        cal.set(2012, 12, 12);
        tour1.setStartDate(cal.getTime());
        tourDAO.update(tour1);
        result = tourDAO.get(tour1Id);
        assertTourDeepEquals(tour1, result);
    }

    @Test
    public void updateTourWithWronAttributes() {
        try {
            tourDAO.update(null);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null id exception
        Tour tour = prepareTour();
        try {
            tourDAO.update(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //not found entity
        tour.setId(1l);
        try {
            tourDAO.update(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null destination
        tour = prepareTour();
        tourDAO.create(tour);
        Long tourId = tour.getId();
        tour.setDestination(null);
        try {
            tourDAO.update(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //empty destination
        tour = prepareTour();
        tour.setId(tourId);
        tour.setDestination("");
        try {
            tourDAO.update(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null start date
        tour = prepareTour();
        tour.setId(tourId);
        tour.setStartDate(null);
        try {
            tourDAO.update(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //less than 0 duration
        tour = prepareTour();
        tour.setId(tourId);
        tour.setDurationInHours(0);
        try {
            tourDAO.update(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

    }

    @Test
    public void deleteTour() {
        Tour tour1 = prepareTour();
        Tour tour2 = prepareTour();
        tour2.setDescription("another entry");

        tourDAO.create(tour1);
        tourDAO.create(tour2);

        assertNotNull(tourDAO.get(tour1.getId()));
        assertNotNull(tourDAO.get(tour2.getId()));

        tourDAO.delete(tour2);

        assertNotNull(tourDAO.get(tour1.getId()));
        assertNull(tourDAO.get(tour2.getId()));
    }

    @Test
    public void deleteTourWithWrongAttributes() {
        try {
            tourDAO.delete(null);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        //null id exception
        Tour tour = prepareTour();
        try {
            tourDAO.delete(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }

        tour = prepareTour();
        tour.setId(1l);
        try {
            tourDAO.delete(tour);
            fail();
        } catch (DataAccessException ex) {
            //OK
        }
    }

    private Tour prepareTour() {
        Tour tour = new Tour();
        tour.setDescription("description");
        tour.setDestination("New York, USA");
        tour.setDurationInHours(5);
        Calendar cal = new GregorianCalendar();
        cal.set(2012, 10, 10);
        tour.setStartDate(cal.getTime());
        return tour;
    }

    private static void assertTourDeepEquals(Tour expected, Tour actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDestination(), actual.getDestination());
        assertEquals(expected.getDurationInHours(), actual.getDurationInHours());
    }
}
