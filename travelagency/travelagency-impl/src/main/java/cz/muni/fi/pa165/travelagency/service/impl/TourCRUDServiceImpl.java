package cz.muni.fi.pa165.travelagency.service.impl;

import cz.muni.fi.pa165.travelagency.data.dao.TourDAO;
import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Tour;
import cz.muni.fi.pa165.travelagency.service.TourCRUDService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michal Jurc
 */
@Service("tourCRUDService")
@Transactional
public class TourCRUDServiceImpl implements TourCRUDService {

    @Autowired
    @Qualifier(value = "tourDAO")
    private TourDAO tourDAO;

    @Override
    public void create(TourDTO tourDTO) {
        if (tourDTO == null) {
            throw new IllegalArgumentException("Tour can't be null");
        }
        if (tourDTO.getId() != null) {
            throw new IllegalArgumentException("New tour ID must be null");
        }
        validateTour(tourDTO);
        Tour tour = tourDTOtoEntity(tourDTO);
        tourDAO.create(tour);
        tourDTO.setId(tour.getId());
    }

    @Override
    public TourDTO get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Tour ID to retrieve can't be null");
        }
        Tour tour = tourDAO.get(id);
        TourDTO result = getTourDTOFromEntity(tour);
        return result;
    }

    @Override
    public void update(TourDTO tourDTO) {
        if (tourDTO == null) {
            throw new IllegalArgumentException("TourDTO to update can't be null");
        }
        if (tourDTO.getId() == null) {
            throw new IllegalArgumentException("TourDTO ID to update can't be null");
        }
        validateTour(tourDTO);
        Tour tour = tourDTOtoEntity(tourDTO);
        tourDAO.update(tour);
    }

    @Override
    public void delete(TourDTO tourDTO) {
        if (tourDTO == null) {
            throw new IllegalArgumentException("TourDTO to delete can't be null");
        }
        if (tourDTO.getId() == null) {
            throw new IllegalArgumentException("TourDTO ID to delete can't be null");
        }
        Tour tour = tourDTOtoEntity(tourDTO);
        tourDAO.delete(tour);
    }

    @Override
    public List<TourDTO> getAll() {
        List<TourDTO> result = new ArrayList<>();
        List<Tour> tours = tourDAO.getAll();
        for (Tour tour : tours) {
            result.add(getTourDTOFromEntity(tour));
        }
        return result;
    }

    private void validateTour(TourDTO tourDTO) {
        if (tourDTO.getDestination() == null) {
            throw new IllegalArgumentException("Tour destination must be set, it's null");
        }
        if (tourDTO.getDestination().isEmpty()) {
            throw new IllegalArgumentException("Tour destination must be set, it's empty");
        }
        if (tourDTO.getStartDate() == null) {
            throw new IllegalArgumentException("Tour start date must be set, it's null");
        }
        if (tourDTO.getDurationInHours() < 1) {
            throw new IllegalArgumentException("Tour duration must be set, it's less than 1");
        }
    }

    private Tour tourDTOtoEntity(TourDTO tourDTO) {
        if (tourDTO == null) {
            return null;
        }
        Tour tour = new Tour();
        tour.setId(tourDTO.getId());
        tour.setDestination(tourDTO.getDestination());
        tour.setDescription(tourDTO.getDescription());
        tour.setDurationInHours(tourDTO.getDurationInHours());
        tour.setStartDate(tourDTO.getStartDate());
        return tour;
    }

    private TourDTO getTourDTOFromEntity(Tour tour) {
        if (tour == null) {
            return null;
        }
        TourDTO tourDTO = new TourDTO();
        tourDTO.setId(tour.getId());
        tourDTO.setDestination(tour.getDestination());
        tourDTO.setDescription(tour.getDescription());
        tourDTO.setStartDate(tour.getStartDate());
        tourDTO.setDurationInHours(tour.getDurationInHours());
        return tourDTO;
    }
}
