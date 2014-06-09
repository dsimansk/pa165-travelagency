package cz.muni.fi.pa165.travelagency.web;

import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.service.CustomerCRUDService;
import cz.muni.fi.pa165.travelagency.web.validator.CustomerValidator;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Michal Jurc
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    private CustomerDTO addedCustomerBackingBean;
    private CustomerDTO editedCustomerBackingBean;
        
    @Autowired
    @Qualifier("customerCRUDService")
    private CustomerCRUDService customerCRUDService;
    
    @Autowired
    @Qualifier("customerValidator")
    private CustomerValidator customerValidator;

        
    @PostConstruct
    public void init(){
        addedCustomerBackingBean = new CustomerDTO();
    }
    
    @RequestMapping(method= RequestMethod.GET) 
    public ModelAndView renderList() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("customers", customerCRUDService.getAll());
        mav.setViewName("customer/list");
        return mav;
    }
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ModelAndView renderDetail(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        editedCustomerBackingBean = customerCRUDService.get(Long.parseLong(id));
        mav.addObject("customer", editedCustomerBackingBean);
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
        if (result.hasErrors()) {
            addedCustomerBackingBean = customerDTO;
            modelMap.put(BindingResult.class.getName() + "addedCustomer", result);
            return "add";
        } else {
            customerCRUDService.create(customerDTO);
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
        Long editedCustomerId = Long.parseLong(id);
        editedCustomerBackingBean = customerCRUDService.get(editedCustomerId);
        mav.addObject("editedCustomer", editedCustomerBackingBean);
        mav.setViewName("customer/edit");
        return mav;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editCustomer(@ModelAttribute("editedCustomer") CustomerDTO editedCustomer,
            @PathVariable String id, BindingResult result, ModelMap modelMap) {
        customerValidator.validate(editedCustomer, result);
        if (result.hasErrors()) {
            modelMap.put("editedCustomerr", editedCustomer);
            modelMap.put(BindingResult.class.getName() + "editedCustomer", result);
            return "customer/edit";
        } else {
            customerCRUDService.update(editedCustomer);
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
        Long deletedCustomerId = Long.parseLong(id);
        CustomerDTO deletedCustomer = customerCRUDService.get(deletedCustomerId);
        mav.addObject("deletedCustomer", deletedCustomer);
        mav.setViewName("customer/delete");
        return mav;
    }
    
    @RequestMapping(value ="/delete/{id}", method = RequestMethod.POST)
    public String deleteCustomer(@PathVariable String id) {
        Long deletedCustomerId = Long.parseLong(id);
        CustomerDTO deletedCustomer = customerCRUDService.get(deletedCustomerId);
        try {
            customerCRUDService.delete(deletedCustomer);
        } catch (JpaSystemException ex) {
            return "redirect:/customer/delete/error";
        }
        return "redirect:/customer/delete/success";
    }
    
    @RequestMapping(value = "/delete/success", method = RequestMethod.GET)
    public ModelAndView deleteSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "customer.delete.success");
        mav.setViewName("customer/success");
        return mav;
    }
    
    @RequestMapping(value = "/delete/error", method = RequestMethod.GET)
    public ModelAndView deleteError() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "customer.delete.error");
        mav.setViewName("customer/success");
        return mav;
    }
    
}
