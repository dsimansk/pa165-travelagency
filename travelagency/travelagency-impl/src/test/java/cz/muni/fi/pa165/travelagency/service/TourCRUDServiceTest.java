/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.data.dao.impl.TourDAOImpl;
import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Tour;
import cz.muni.fi.pa165.travelagency.service.impl.TourCRUDServiceImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
 * @author David Simansky <d.simansky@gmail.com>
 */
@RunWith(MockitoJUnitRunner.class)
public class TourCRUDServiceTest {

    @Mock
    private TourDAOImpl tourDAO;
    @InjectMocks
    private TourCRUDServiceImpl tourCRUDService;

    @Test
    public void createTour() {
        TourDTO tourDTO = prepareTourDTO();
        Tour tour = prepareTourEntity();
        tourCRUDService.create(tourDTO);

        verify(tourDAO).create(tour);
        verify(tourDAO, times(0)).get(any(Long.class));
        verify(tourDAO, times(0)).update(any(Tour.class));
        verify(tourDAO, times(0)).delete(any(Tour.class));
        verify(tourDAO, times(0)).getAll();
    }

    @Test
    public void createTourExceptions() {
        TourDTO tourDTO = null;
        try {
            tourCRUDService.create(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        tourDTO = prepareTourDTO();
        tourDTO.setId(1l);
        try {
            tourCRUDService.create(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void tourValidationExceptions() {
        TourDTO tourDTO = prepareTourDTO();

        tourDTO.setDestination(null);
        try {
            tourCRUDService.create(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        tourDTO.setDestination("");
        try {
            tourCRUDService.create(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        tourDTO = prepareTourDTO();
        tourDTO.setStartDate(null);
        try {
            tourCRUDService.create(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        tourDTO.setDurationInHours(0);
        try {
            tourCRUDService.create(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

    }

    @Test
    public void getTour() {
        Tour tour = prepareTourEntity();
        tour.setId(1l);
        when(tourDAO.get(1l)).thenReturn(tour);
        TourDTO expected = prepareTourDTO();
        expected.setId(1l);
        TourDTO actual = tourCRUDService.get(1l);

        verify(tourDAO).get(1l);
        verify(tourDAO, times(0)).create(any(Tour.class));
        verify(tourDAO, times(0)).update(any(Tour.class));
        verify(tourDAO, times(0)).delete(any(Tour.class));
        verify(tourDAO, times(0)).getAll();

        assertDeepEquals(expected, actual);
    }

    @Test
    public void getTourExceptions() {
        try {
            tourCRUDService.get(null);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void updateTour() {
        TourDTO tourDTO = prepareTourDTO();
        tourDTO.setId(1l);
        Tour tour = prepareTourEntity();
        tour.setId(1l);
        tourCRUDService.update(tourDTO);

        verify(tourDAO).update(tour);
        verify(tourDAO, times(0)).create(any(Tour.class));
        verify(tourDAO, times(0)).get(any(Long.class));
        verify(tourDAO, times(0)).delete(any(Tour.class));
        verify(tourDAO, times(0)).getAll();
    }

    @Test
    public void updateTourExceptions() {
        TourDTO tourDTO = null;
        try {
            tourCRUDService.update(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        tourDTO = prepareTourDTO();
        try {
            tourCRUDService.update(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void deleteTour() {
        TourDTO tourDTO = prepareTourDTO();
        tourDTO.setId(1l);
        Tour tour = prepareTourEntity();
        tour.setId(1l);
        tourCRUDService.delete(tourDTO);

        verify(tourDAO).delete(tour);
        verify(tourDAO, times(0)).create(any(Tour.class));
        verify(tourDAO, times(0)).get(any(Long.class));
        verify(tourDAO, times(0)).update(any(Tour.class));
        verify(tourDAO, times(0)).getAll();
    }

    @Test
    public void deleteTourExceptions() {
        TourDTO tourDTO = null;
        try {
            tourCRUDService.delete(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }

        tourDTO = prepareTourDTO();
        try {
            tourCRUDService.delete(tourDTO);
            fail();
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void getAllTours() {
        List<Tour> tours = new ArrayList<>();
        Tour tour1 = prepareTourEntity();
        tour1.setId(1l);
        tours.add(tour1);
        Tour tour2 = prepareTourEntity();
        tour2.setId(2l);
        tours.add(tour2);
        Tour tour3 = prepareTourEntity();
        tour3.setId(3l);
        tours.add(tour3);
        when(tourDAO.getAll()).thenReturn(tours);
        List<TourDTO> expected = new ArrayList<>();
        TourDTO tourDTO1 = prepareTourDTO();
        tourDTO1.setId(1l);
        expected.add(tourDTO1);
        TourDTO tourDTO2 = prepareTourDTO();
        tourDTO2.setId(2l);
        expected.add(tourDTO2);
        TourDTO tourDTO3 = prepareTourDTO();
        tourDTO3.setId(3l);
        expected.add(tourDTO3);

        List<TourDTO> actual = tourCRUDService.getAll();

        verify(tourDAO).getAll();
        verify(tourDAO, times(0)).create(any(Tour.class));
        verify(tourDAO, times(0)).get(any(Long.class));
        verify(tourDAO, times(0)).update(any(Tour.class));
        verify(tourDAO, times(0)).delete(any(Tour.class));

        assertDeepEquals(expected, actual);
    }

    private TourDTO prepareTourDTO() {
        TourDTO tour = new TourDTO();
        tour.setDescription("description");
        tour.setDestination("New York, USA");
        tour.setDurationInHours(5);
        Calendar cal = new GregorianCalendar();
        cal.set(2012, 10, 10);
        tour.setStartDate(cal.getTime());
        return tour;
    }

    private Tour prepareTourEntity() {
        Tour tour = new Tour();
        tour.setDescription("description");
        tour.setDestination("New York, USA");
        tour.setDurationInHours(5);
        Calendar cal = new GregorianCalendar();
        cal.set(2012, 10, 10);
        tour.setStartDate(cal.getTime());
        return tour;
    }

    private void assertDeepEquals(TourDTO expected, TourDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDestination(), actual.getDestination());
        assertEquals(expected.getDurationInHours(), actual.getDurationInHours());
    }

    private void assertDeepEquals(List<TourDTO> expectedList, List<TourDTO> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            TourDTO expected = expectedList.get(i);
            TourDTO actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
}
