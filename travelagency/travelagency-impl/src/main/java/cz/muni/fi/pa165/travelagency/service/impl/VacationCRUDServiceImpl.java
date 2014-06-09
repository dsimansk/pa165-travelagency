package cz.muni.fi.pa165.travelagency.service.impl;

import cz.muni.fi.pa165.travelagency.data.dao.TourDAO;
import cz.muni.fi.pa165.travelagency.data.dao.VacationDAO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Tour;
import cz.muni.fi.pa165.travelagency.data.entity.Vacation;
import cz.muni.fi.pa165.travelagency.service.VacationCRUDService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author David Simansky
 */
@Service("vacationCRUDService")
@Transactional
public class VacationCRUDServiceImpl implements VacationCRUDService {

    @Autowired
    @Qualifier(value = "vacationDAO")
    private VacationDAO vacationDAO;
    @Autowired
    @Qualifier(value = "tourDAO")
    private TourDAO tourDAO;

    @Override
    public void create(VacationDTO vacationDTO) {
        if (vacationDTO == null) {
            throw new IllegalArgumentException("Vacation can't be null");
        }
        if (vacationDTO.getId() != null) {
            throw new IllegalArgumentException("New vacation ID must be null");
        }
        validateVacation(vacationDTO);
        Vacation vacation = vacationDTOtoEntity(vacationDTO);
        vacationDAO.create(vacation);
        vacationDTO.setId(vacation.getId());
    }

    @Override
    public VacationDTO get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Vacation ID to retrieve can't be null");
        }
        Vacation vacation = vacationDAO.get(id);
        VacationDTO result = getVacationDTOFromEntity(vacation);
        return result;
    }

    @Override
    public void update(VacationDTO vacationDTO) {
        if (vacationDTO == null) {
            throw new IllegalArgumentException("VacationDTO to update can't be null");
        }
        if (vacationDTO.getId() == null) {
            throw new IllegalArgumentException("VacationDTO ID to update can't be null");
        }
        validateVacation(vacationDTO);
        Vacation vacation = vacationDTOtoEntity(vacationDTO);
        vacationDAO.update(vacation);
    }

    @Override
    public void delete(VacationDTO vacationDTO) {
        if (vacationDTO == null) {
            throw new IllegalArgumentException("VacationDTO to delete can't be null");
        }
        if (vacationDTO.getId() == null) {
            throw new IllegalArgumentException("VacationDTO ID to delete can't be null");
        }
        Vacation vacation = vacationDTOtoEntity(vacationDTO);
        vacationDAO.delete(vacation);
    }

    @Override
    public List<VacationDTO> getAll() {
        List<VacationDTO> result = new ArrayList<>();
        List<Vacation> vacations = vacationDAO.getAll();
        for (Vacation vacation : vacations) {
            result.add(getVacationDTOFromEntity(vacation));
        }
        return result;
    }

    private void validateVacation(VacationDTO vacationDTO) {
        if (vacationDTO.getDestination() == null) {
            throw new IllegalArgumentException("Vacation's destination must be "
                    + "set, it is currently null.");
        }
        if (vacationDTO.getDestination().isEmpty()) {
            throw new IllegalArgumentException("Vacation's destination must be "
                    + "set, it is currently empty.");
        }
        if (vacationDTO.getStartDate() == null) {
            throw new IllegalArgumentException("Vacation's start date must be "
                    + "set, it is currently null.");
        }
        if (vacationDTO.getEndDate() == null) {
            throw new IllegalArgumentException("Vacation's end date must be "
                    + "set, it is currently null.");
        }
        if (vacationDTO.getEndDate().compareTo(vacationDTO.getStartDate()) < 0) {
            throw new IllegalArgumentException("Vacation's start date is after "
                    + "its end date.");
        }
        if (vacationDTO.getPrice() == null) {
            throw new IllegalArgumentException("Vacation's price must be set, "
                    + "it is currently null.");
        }
        if (vacationDTO.getMaxCapacity() < 1) {
            throw new IllegalArgumentException("Vacation's capacity must be "
                    + "positive.");
        }
    }

    private List<Tour> getTours(List<Long> listOfId) {
        List<Tour> result = new ArrayList<>();
        for (Long id : listOfId) {
            Tour tour = tourDAO.get(id);
            result.add(tour);
        }
        return result;
    }

    private Vacation vacationDTOtoEntity(VacationDTO vacationDTO) {
        if (vacationDTO == null) {
            return null;
        }
        Vacation vacation = new Vacation();
        vacation.setId(vacationDTO.getId());
        vacation.setDestination(vacationDTO.getDestination());
        vacation.setStartDate(vacationDTO.getStartDate());
        vacation.setEndDate(vacationDTO.getEndDate());
        vacation.setMaxCapacity(vacationDTO.getMaxCapacity());
        vacation.setPrice(vacationDTO.getPrice());
        vacation.setReserved(vacationDTO.getReserved());

        List<Tour> tours = getTours(vacationDTO.getTours());
        vacation.setTours(tours);

        return vacation;
    }

    private VacationDTO getVacationDTOFromEntity(Vacation vacation) {
        if (vacation == null) {
            return null;
        }
        VacationDTO vacationDTO = new VacationDTO();
        vacationDTO.setId(vacation.getId());
        vacationDTO.setDestination(vacation.getDestination());
        vacationDTO.setStartDate(vacation.getStartDate());
        vacationDTO.setEndDate(vacation.getEndDate());
        vacationDTO.setPrice(vacation.getPrice());
        vacationDTO.setMaxCapacity(vacation.getMaxCapacity());
        vacationDTO.setReserved(vacation.getReserved());
        List<Long> tours = new ArrayList<>();
        for (Tour tour : vacation.getTours()) {
            tours.add(tour.getId());
        }
        vacationDTO.setTours(tours);
        return vacationDTO;
    }

    @Override
    public List<VacationDTO> getByDestination(String destination) {
        if (destination == null || destination.isEmpty()) {
            throw new IllegalArgumentException("Destination can't be null!");
        }
        List<VacationDTO> result = new ArrayList<>();
        List<Vacation> vacations = vacationDAO.getByDestination(destination);
        for(Vacation vac : vacations) {
            result.add(getVacationDTOFromEntity(vac));
        }
        return result;
    }

    @Override
    public List<String> getAllDestinations() {
        return vacationDAO.getAllDestinations();
    }
}
