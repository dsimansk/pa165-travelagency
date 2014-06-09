package cz.muni.fi.pa165.travelagencyclient.web;

import cz.muni.fi.pa165.travelagencyclient.web.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagencyclient.web.dto.CustomerDTOResponse;
import cz.muni.fi.pa165.travelagencyclient.web.validator.CustomerValidator;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Michal Jurc
 */
@Controller
@RequestMapping("/customer")
public class CustomerClientController {

    private CustomerDTO addedCustomerBackingBean;
    private CustomerDTO editedCustomerBackingBean;
    @Autowired
    @Qualifier("customerValidator")
    private CustomerValidator customerValidator;
    private String remoteHost = "http://localhost:8080/pa165/rest/";
    
    @Autowired
    private RestTemplate rest;

    @PostConstruct
    public void init() {
        addedCustomerBackingBean = new CustomerDTO();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView renderList() {
        String url = remoteHost + "customer/";
        CustomerDTOResponse resp = rest.getForObject(url, CustomerDTOResponse.class);
        ModelAndView mav = new ModelAndView();
        mav.addObject("customers", resp.getListData());
        mav.setViewName("customer/list");
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView renderDetail(@PathVariable String id) {
        String url = remoteHost + "customer/" + id;
        ModelAndView mav = new ModelAndView();
        mav.addObject("customer", getRemoteCustomer(id));
        mav.setViewName("customer/detail");
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView renderAdd() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("addedCustomer", addedCustomerBackingBean);
        mav.setViewName("customer/add");
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCustomer(@ModelAttribute("addedCustomer") CustomerDTO customerDTO,
            BindingResult result, ModelMap modelMap) {
        customerValidator.validate(customerDTO, result);
        String url = remoteHost + "customer/";
        if (result.hasErrors()) {
            addedCustomerBackingBean = customerDTO;
            modelMap.put(BindingResult.class.getName() + "addedCustomer", result);
            return "customer/add";
        } else {
            rest.postForObject(url, customerDTO, CustomerDTO.class);
            return "redirect:/customer/add/success";
        }
    }

    @RequestMapping(value = "/add/success", method = RequestMethod.GET)
    public ModelAndView addSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "customer.add.success");
        mav.setViewName("customer/success");
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView renderEdit(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        editedCustomerBackingBean = getRemoteCustomer(id);
        mav.addObject("editedCustomer", editedCustomerBackingBean);
        mav.setViewName("customer/edit");
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editCustomer(@ModelAttribute("editedCustomer") CustomerDTO editedCustomer,
            @PathVariable String id, BindingResult result, ModelMap modelMap) {
        customerValidator.validate(editedCustomer, result);
        String url = remoteHost + "customer/";
        if (result.hasErrors()) {
            modelMap.put("editedCustomer", editedCustomer);
            modelMap.put(BindingResult.class.getName() + "editedCustomer", result);
            return "customer/edit";
        } else {
            rest.put(url, editedCustomer);
            return "redirect:/customer/edit/success";
        }
    }

    @RequestMapping(value = "/edit/success", method = RequestMethod.GET)
    public ModelAndView editSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "customer.edit.success");
        mav.setViewName("customer/success");
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView renderDelete(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        CustomerDTO deletedCustomer = getRemoteCustomer(id);
        mav.addObject("deletedCustomer", deletedCustomer);
        mav.setViewName("customer/delete");
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteCustomer(@PathVariable String id) {
        String url = remoteHost + "customer/" + id;
        rest.delete(url);
        return "redirect:/customer/delete/success";
    }

    @RequestMapping(value = "/delete/success", method = RequestMethod.GET)
    public ModelAndView deleteSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "customer.delete.success");
        mav.setViewName("customer/success");
        return mav;
    }

    @ExceptionHandler(RestClientException.class)
    public ModelAndView badFormat(RestClientException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Remote host " + remoteHost + " returned error code:");
        if (ex.contains(IOException.class)) {
            mav.addObject("code", "503 Service Unavailable");
        } else {
            mav.addObject("code", ex.getMessage());
        }
        mav.setViewName("customer/error");

        return mav;
    }

    private CustomerDTO getRemoteCustomer(String id) throws RestClientException {
        String url = remoteHost + "customer/" + id;
        CustomerDTOResponse resp = rest.getForObject(url, CustomerDTOResponse.class);
        CustomerDTO tour = resp.getCustomerDTO();
        return tour;
    }
}
