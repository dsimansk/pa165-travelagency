package cz.muni.fi.pa165.travelagency.web.rest;

import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.service.CustomerCRUDService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 *
 * @author Michal Jurc
 */
@Controller
@RequestMapping("/rest/customer")
public class CustomerRESTController {

    @Autowired
    @Qualifier("customerCRUDService")
    private CustomerCRUDService customerCRUDService;
    @Autowired
    private View jsonView;
    private static final String DATA_FIELD = "customerDTO";
    private static final String LIST_FIELD = "listData";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView getCustomers() {
        List<CustomerDTO> customers = customerCRUDService.getAll();
        return new ModelAndView(jsonView, LIST_FIELD, customers);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createCustomer(@RequestBody CustomerDTO customer,
            WebRequest request) {
        customerCRUDService.create(customer);
        CustomerDTO createdCustomer = customerCRUDService.get(customer.getId());
        return new ModelAndView(jsonView, DATA_FIELD, createdCustomer);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView updateCustomer(@RequestBody CustomerDTO customer,
            WebRequest request) {
        customerCRUDService.update(customer);
        CustomerDTO updatedCustomer = customerCRUDService.get(customer.getId());
        return new ModelAndView(jsonView, DATA_FIELD, updatedCustomer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView getCustomer(@PathVariable("id") String id) {
        Long customerId = Long.parseLong(id);
        CustomerDTO customerDTO = customerCRUDService.get(customerId);
        if (customerDTO == null) {
            throw new IllegalArgumentException();
        }
        return new ModelAndView(jsonView, DATA_FIELD, customerDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView deleteCustomer(@PathVariable("id") String id) {
        Long customerId = Long.parseLong(id);
        CustomerDTO customerDTO = customerCRUDService.get(customerId);
        if (customerDTO == null) {
            throw new IllegalArgumentException();
        }
        customerCRUDService.delete(customerDTO);
        return new ModelAndView(jsonView, DATA_FIELD, null);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Error while accessing the database.")
    public void notAcessible() {
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such customer.")
    public void notFound() {
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong id.")
    public void badFormat() {
    }
}
