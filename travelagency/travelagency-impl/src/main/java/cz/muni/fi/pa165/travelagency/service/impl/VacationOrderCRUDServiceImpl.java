package cz.muni.fi.pa165.travelagency.service.impl;

import cz.muni.fi.pa165.travelagency.data.dao.TourDAO;
import cz.muni.fi.pa165.travelagency.data.dao.VacationOrderDAO;
import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationOrderDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Customer;
import cz.muni.fi.pa165.travelagency.data.entity.Tour;
import cz.muni.fi.pa165.travelagency.data.entity.Vacation;
import cz.muni.fi.pa165.travelagency.data.entity.VacationOrder;
import cz.muni.fi.pa165.travelagency.service.VacationCRUDService;
import cz.muni.fi.pa165.travelagency.service.VacationOrderCRUDService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martin Gerlasinsky
 */
@Service("vacationOrderCRUDService")
@Transactional
public class VacationOrderCRUDServiceImpl implements VacationOrderCRUDService {

    @Autowired
    @Qualifier(value = "vacationOrderDAO")
    private VacationOrderDAO vacationOrderDAO;
    @Autowired
    @Qualifier(value = "tourDAO")
    private TourDAO tourDAO;
    @Autowired
    @Qualifier(value = "vacationCRUDService")
    private VacationCRUDService vacationCRUDService;

    @Override
    public void create(VacationOrderDTO vacationOrderDTO) {
        if (vacationOrderDTO == null) {
            throw new IllegalArgumentException("VacationOrder can't be null");
        }
        if (vacationOrderDTO.getId() != null) {
            throw new IllegalArgumentException("New vacationOrder ID must be null");
        }
        validateVacationOrder(vacationOrderDTO);
        VacationDTO vacation = vacationOrderDTO.getVacation();
        int reserved = vacation.getReserved() + 1;
        if (reserved > vacation.getMaxCapacity()) {
            throw new IllegalArgumentException("The selected vacation is full.");
        }
        vacation.setReserved(reserved);
        vacationCRUDService.update(vacation);
        VacationOrder vacationOrder = vacationOrderDTOtoEntity(vacationOrderDTO);
        vacationOrderDAO.create(vacationOrder);
        vacationOrderDTO.setId(vacationOrder.getId());
    }

    @Override
    public VacationOrderDTO get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("VacationOrder ID to retrieve can't be null");
        }
        VacationOrder vacationOrder = vacationOrderDAO.get(id);
        VacationOrderDTO result = getVacationOrderDTOFromEntity(vacationOrder);
        return result;
    }

    @Override
    public void update(VacationOrderDTO vacationOrderDTO) {
        if (vacationOrderDTO == null) {
            throw new IllegalArgumentException("VacationOrderDTO to update can't be null");
        }
        if (vacationOrderDTO.getId() == null) {
            throw new IllegalArgumentException("VacationOrderDTO ID to update can't be null");
        }
        validateVacationOrder(vacationOrderDTO);
        VacationOrder vacationOrder = vacationOrderDTOtoEntity(vacationOrderDTO);
        vacationOrderDAO.update(vacationOrder);
    }

    @Override
    public void delete(VacationOrderDTO vacationOrderDTO) {
        if (vacationOrderDTO == null) {
            throw new IllegalArgumentException("VacationOrderDTO to delete can't be null");
        }
        if (vacationOrderDTO.getId() == null) {
            throw new IllegalArgumentException("VacationOrderDTO ID to delete can't be null");
        }
        VacationDTO vacation = vacationOrderDTO.getVacation();
        int reserved = vacation.getReserved() - 1;
        vacation.setReserved(reserved);
        vacationCRUDService.update(vacation);
        VacationOrder vacationOrder = vacationOrderDTOtoEntity(vacationOrderDTO);
        vacationOrderDAO.delete(vacationOrder);
    }

    @Override
    public List<VacationOrderDTO> getAll() {
        List<VacationOrderDTO> result = new ArrayList<>();
        List<VacationOrder> vacationOrders = vacationOrderDAO.getAll();
        for (VacationOrder vacationOrder : vacationOrders) {
            result.add(getVacationOrderDTOFromEntity(vacationOrder));
        }
        return result;
    }

    @Override
    public List<VacationOrderDTO> getOrdersByCustomer(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("VacationOrder ID to retrieve can't be null");
        }
        Customer customer = new Customer();
        customer.setId(customerId);
        List<VacationOrder> ordersByCustomer = vacationOrderDAO.getOrdersByCustomer(customer);
        List<VacationOrderDTO> result = new ArrayList<>();
        for (VacationOrder vacationOrder : ordersByCustomer) {
            result.add(getVacationOrderDTOFromEntity(vacationOrder));
        }
        return result;

    }

    private VacationOrder vacationOrderDTOtoEntity(VacationOrderDTO vacationOrderDTO) {
        if (vacationOrderDTO == null) {
            return null;
        }
        VacationOrder vacationOrder = new VacationOrder();
        vacationOrder.setId(vacationOrderDTO.getId());

        Customer customer = customerDTOToEntity(vacationOrderDTO.getCustomer());
        vacationOrder.setCustomer(customer);

        Vacation vacation = vacationDTOtoEntity(vacationOrderDTO.getVacation());
        vacationOrder.setVacation(vacation);

        vacationOrder.setReservations(vacationOrderDTO.getReservations());
        vacationOrder.setTours(getTours(vacationOrderDTO.getTours()));
        return vacationOrder;
    }

    private VacationOrderDTO getVacationOrderDTOFromEntity(VacationOrder vacationOrder) {
        if (vacationOrder == null) {
            return null;
        }
        VacationOrderDTO result = new VacationOrderDTO();
        result.setId(vacationOrder.getId());
        result.setCustomer(getCustomerDTOFromEntity(vacationOrder.getCustomer()));
        result.setVacation(getVacationDTOFromEntity(vacationOrder.getVacation()));
        result.setTours(getTourIds(vacationOrder.getTours()));
        result.setReservations(vacationOrder.getReservations());
        return result;
    }

    private Customer customerDTOToEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        return customer;
    }

    private CustomerDTO getCustomerDTOFromEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        return customerDTO;
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
        vacationDTO.setMaxCapacity(vacation.getMaxCapacity());
        vacationDTO.setPrice(vacation.getPrice());
        vacationDTO.setReserved(vacation.getReserved());
        vacationDTO.setTours(getTourIds(vacation.getTours()));
        return vacationDTO;
    }

    private List<Tour> getTours(List<Long> listOfId) {
        List<Tour> result = new ArrayList<>();
        for (Long id : listOfId) {
            Tour tour = tourDAO.get(id);
            result.add(tour);
        }
        return result;
    }

    private List<Long> getTourIds(List<Tour> tours) {
        List<Long> listOfId = new ArrayList<>();
        for (Tour tour : tours) {
            listOfId.add(tour.getId());
        }
        return listOfId;
    }

    private void validateVacationOrder(VacationOrderDTO orderDTO) {
        if (orderDTO.getCustomer() == null) {
            throw new IllegalArgumentException("Order's customer must be set, "
                    + "it is currently null.");
        }
        if (orderDTO.getVacation() == null) {
            throw new IllegalArgumentException("Order's vacation must be set, "
                    + "it is currently null.");
        }
        if (orderDTO.getReservations() < 0) {
            throw new IllegalArgumentException("Order's reservations must be"
                    + "positive.");
        }
    }
}
