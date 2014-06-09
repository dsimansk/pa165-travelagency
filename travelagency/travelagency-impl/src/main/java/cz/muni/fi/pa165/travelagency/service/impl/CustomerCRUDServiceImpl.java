package cz.muni.fi.pa165.travelagency.service.impl;

import cz.muni.fi.pa165.travelagency.data.dao.CustomerDAO;
import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Customer;
import cz.muni.fi.pa165.travelagency.service.CustomerCRUDService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian Kunec
 */
@Service("customerCRUDService")
@Transactional
public class CustomerCRUDServiceImpl implements CustomerCRUDService {

    @Autowired
    @Qualifier(value = "customerDAO")
    private CustomerDAO customerDAO;

    @Override
    public void create(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new IllegalArgumentException("Customer can't be null");
        }
        if (customerDTO.getId() != null) {
            throw new IllegalArgumentException("New customer ID must be null");
        }
        validateCustomer(customerDTO);
        Customer customer = customerDTOToEntity(customerDTO);
        customerDAO.create(customer);
        customerDTO.setId(customer.getId());
    }

    @Override
    public CustomerDTO get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Customer ID to retrieve can't be null");
        }
        Customer customer = customerDAO.get(id);
        CustomerDTO result = getCustomerDTOFromEntity(customer);
        return result;
    }

    @Override
    public void update(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new IllegalArgumentException("CustomerDTO to update can't be null");
        }
        if (customerDTO.getId() == null) {
            throw new IllegalArgumentException("CustomerDTO ID to update can't be null");
        }
        validateCustomer(customerDTO);
        Customer customer = customerDTOToEntity(customerDTO);
        customerDAO.update(customer);
    }

    @Override
    public void delete(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new IllegalArgumentException("CustomerDTO to delete can't be null");
        }
        if (customerDTO.getId() == null) {
            throw new IllegalArgumentException("CustomerDTO ID to delete can't be null");
        }
        Customer customer = customerDTOToEntity(customerDTO);
        customerDAO.delete(customer);
    }

    @Override
    public List<CustomerDTO> getAll() {
        List<CustomerDTO> result = new ArrayList<>();
        List<Customer> customers = customerDAO.getAll();
        for (Customer customer : customers) {
            result.add(getCustomerDTOFromEntity(customer));
        }
        return result;
    }

    private void validateCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getName() == null) {
            throw new IllegalArgumentException("Customer name must be set, it's null");
        }
        if (customerDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Customer name must be set, it's empty");
        }
        if (customerDTO.getAddress() == null) {
            throw new IllegalArgumentException("Customer address must be set, it's null");
        }
        if (customerDTO.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Customer address must be set, it's null");
        }
        if (customerDTO.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Customer phone no. must be set, it's null");
        }
        if (customerDTO.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Customer phone no. must be set, it's null");
        }

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
}
