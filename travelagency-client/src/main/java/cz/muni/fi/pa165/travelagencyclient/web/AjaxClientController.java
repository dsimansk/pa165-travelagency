package cz.muni.fi.pa165.travelagencyclient.web;

import cz.muni.fi.pa165.travelagencyclient.web.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagencyclient.web.dto.CustomerDTOResponse;
import cz.muni.fi.pa165.travelagencyclient.web.validator.CustomerValidator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author David Simansky <d.simansky@gmail.com>
 */
@Controller
@RequestMapping("/ajax/customer")
public class AjaxClientController {

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
    public String renderIndex() {
        return "/ajax/index";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView renderList() {
        String url = remoteHost + "customer/";
        CustomerDTOResponse resp = rest.getForObject(url, CustomerDTOResponse.class);
        ModelAndView mav = new ModelAndView();
        mav.addObject("customers", resp.getListData());
        mav.setViewName("ajax/list");
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView renderAdd() {
        ModelAndView mav = new ModelAndView();
        addedCustomerBackingBean = new CustomerDTO();
        mav.addObject("addedCustomer", addedCustomerBackingBean);
        mav.setViewName("ajax/add");
        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody String addCustomer(@ModelAttribute("addedCustomer") CustomerDTO customerDTO,
            BindingResult result, ModelMap modelMap) {
        customerValidator.validate(customerDTO, result);
        String url = remoteHost + "customer/";
        if (result.hasErrors()) {
            addedCustomerBackingBean = customerDTO;
            modelMap.put(BindingResult.class.getName() + "addedCustomer", result);
            return "Error! All fields must be filled.";
        } else {
            rest.postForObject(url, customerDTO, CustomerDTO.class);
            return "Success! New customer added.";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView renderEdit(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        editedCustomerBackingBean = getRemoteCustomer(id);
        mav.addObject("editedCustomer", editedCustomerBackingBean);
        mav.setViewName("ajax/edit");
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public @ResponseBody String editCustomer(@ModelAttribute("editedCustomer") CustomerDTO editedCustomer,
            @PathVariable String id, BindingResult result, ModelMap modelMap) {
        customerValidator.validate(editedCustomer, result);
        String url = remoteHost + "customer/";
        Map<String, String> map= new HashMap<>();
        if (result.hasErrors()) {
            modelMap.put("editedCustomer", editedCustomer);
            modelMap.put(BindingResult.class.getName() + "editedCustomer", result);
            //map.put("error", );
            return "Error! All fields must be filled.";
        } else {
            rest.put(url, editedCustomer);
            //map.put("message", 
            return "Success! Customer #ID "+id+" edited.";
        }
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView renderDelete(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        CustomerDTO deletedCustomer = getRemoteCustomer(id);
        mav.addObject("deletedCustomer", deletedCustomer);
        mav.setViewName("ajax/delete");
        return mav;
    }
    
    @RequestMapping(value ="/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String deleteCustomer(@PathVariable String id) {
        String url = remoteHost + "customer/" + id;
        rest.delete(url);
        return "Success! Customer #ID "+id+" deleted.";
    }

    private CustomerDTO getRemoteCustomer(String id) throws RestClientException {
        String url = remoteHost + "customer/" + id;
        CustomerDTOResponse resp = rest.getForObject(url, CustomerDTOResponse.class);
        CustomerDTO tour = resp.getCustomerDTO();
        return tour;
    }

    @ExceptionHandler(RestClientException.class)
    public @ResponseBody
    String badFormat(RestClientException ex) {
        ModelAndView mav = new ModelAndView();
        if (ex.contains(IOException.class)) {
            return ("Error! Remote host " + remoteHost + " returned error code: 503 Service Unavailable");
        } else {
            return ("Error! Remote host " + remoteHost + " returned error code: " + ex.getMessage());
        }

    }
}
