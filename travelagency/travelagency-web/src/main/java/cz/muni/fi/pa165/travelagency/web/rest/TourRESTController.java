package cz.muni.fi.pa165.travelagency.web.rest;

import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import cz.muni.fi.pa165.travelagency.service.TourCRUDService;
import cz.muni.fi.pa165.travelagency.web.validator.TourValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 *
 * @author Dávid Šimanský <d.simansky@gmail.com>
 */
@Controller
@RequestMapping("/rest/tour")
public class TourRESTController {

    @Autowired
    private View jsonView;
    @Autowired
    @Qualifier(value = "tourCRUDService")
    private TourCRUDService tourCRUDService;
    private static final String DATA_FIELD = "tourDTO";
    private static final String LIST_FIELD = "listData";

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView getTour(@PathVariable("id") String tourId) {
        Long id;
        TourDTO tour;
        id = Long.parseLong(tourId);
        tour = tourCRUDService.get(id);
        if (tour == null) {
            throw new IllegalArgumentException();
        }
        return new ModelAndView(jsonView, DATA_FIELD, tour);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView getAllTours() {
        List<TourDTO> tours;
        tours = tourCRUDService.getAll();
        return new ModelAndView(jsonView, LIST_FIELD, tours);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ModelAndView createTour(@RequestBody TourDTO tour) {
        TourDTO createdTour;
        tourCRUDService.create(tour);
        createdTour = tourCRUDService.get(tour.getId());
        return new ModelAndView(jsonView, DATA_FIELD, createdTour);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView updateTour(@RequestBody TourDTO tour) {
        TourDTO updatedTour;
        tourCRUDService.update(tour);
        updatedTour = tourCRUDService.get(tour.getId());
        return new ModelAndView(jsonView, DATA_FIELD, updatedTour);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView deleteTour(@PathVariable("id") String tourId) {
        Long id = Long.parseLong(tourId);
        TourDTO tour = tourCRUDService.get(id);
        if (tour == null) {
            throw new IllegalArgumentException();
        }
        tourCRUDService.delete(tour);
        return new ModelAndView(jsonView, DATA_FIELD, null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Tour not found")
    public void notFound() {
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Error during DB access")
    public void notAccessible() {
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "ID can't be parsed")
    public void badFormat() {
    }
}
