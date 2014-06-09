package cz.muni.fi.pa165.travelagency.web;

import cz.muni.fi.pa165.travelagency.data.dto.AccountDTO;
import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.data.dto.RegistrationDTO;
import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import cz.muni.fi.pa165.travelagency.service.AccountCRUDService;
import cz.muni.fi.pa165.travelagency.service.CustomerCRUDService;
import cz.muni.fi.pa165.travelagency.service.RegistrationService;
import cz.muni.fi.pa165.travelagency.service.TourCRUDService;
import cz.muni.fi.pa165.travelagency.service.VacationCRUDService;
import cz.muni.fi.pa165.travelagency.service.adapter.AccountDetailsAdapter;
import static cz.muni.fi.pa165.travelagency.util.Util.*;
import cz.muni.fi.pa165.travelagency.web.util.StringForm;
import cz.muni.fi.pa165.travelagency.web.validator.RegistrationValidator;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Michal Jurc
 */
@Controller
public class HomeController {
    
    @Autowired
    @Qualifier("accountCRUDService")
    private AccountCRUDService accountCRUDService;
    
    @Autowired
    @Qualifier("customerCRUDService")
    private CustomerCRUDService customerCRUDService;
    
    @Autowired
    @Qualifier("tourCRUDService")
    private TourCRUDService tourCRUDService;
    
    @Autowired
    @Qualifier("vacationCRUDService")
    private VacationCRUDService vacationCRUDService;
    
    @Autowired
    @Qualifier("registrationService")
    private RegistrationService registrationService;
    
    @Autowired
    @Qualifier("registrationValidator")
    private RegistrationValidator registrationValidator;
    
    private RegistrationDTO registrationBackingBean;
    private StringForm searchFormBackingBean;
    
    private String error = "";
    
    @PostConstruct
    public void init() {
        CustomerDTO c1 = prepareCustomerDTO();
        CustomerDTO c2 = prepareCustomerDTO();
        c2.setName("Jan Novak");
        CustomerDTO c3 = prepareCustomerDTO();
        c3.setName("John Doe");
        customerCRUDService.create(c1);
        customerCRUDService.create(c2);
        customerCRUDService.create(c3);
        TourDTO t1 = prepareTourDTO();
        TourDTO t2 = prepareTourDTO();
        t2.setDestination("Presov");
        TourDTO t3 = prepareTourDTO();
        t3.setDestination("Kosice");
        tourCRUDService.create(t1);
        tourCRUDService.create(t2);
        tourCRUDService.create(t3);
        VacationDTO v1 = prepareVacationDTO();
        VacationDTO v2 = prepareVacationDTO();
        v2.setDestination("Presov");
        VacationDTO v3 = prepareVacationDTO();
        v3.setDestination("Kosice");
        List<Long> tours = new ArrayList<>();
        for (TourDTO tour : tourCRUDService.getAll()){
            tours.add(tour.getId());
        }
        v1.setTours(tours);
        vacationCRUDService.create(v1);
        vacationCRUDService.create(v2);
        vacationCRUDService.create(v3);
        AccountDTO a1 = prepareAccountDTO();
        a1.setCustomerId(new Long(1));
        accountCRUDService.create(a1);
        AccountDTO a2 = prepareAccountDTO();
        a2.setCustomerId(new Long(2));
        a2.setName("customer");
        a2.setPassword("customer");
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_CUSTOMER");
        a2.setRoles(roles);
        accountCRUDService.create(a2);
        registrationBackingBean = new RegistrationDTO();
        searchFormBackingBean = new StringForm("");
    }
        
    @RequestMapping("/")
    public ModelAndView renderHome() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("vacations", vacationCRUDService.getAll());
        List<String> vacationDestinations = vacationCRUDService.getAllDestinations();
        List<String> destinations = new ArrayList<>();
        for (String vacationDestination : vacationDestinations) {
            vacationDestination = "\"" + vacationDestination + "\"";
            destinations.add(vacationDestination);
        }
        mav.addObject("vacationDestinations", destinations);
        mav.addObject("searchForm", searchFormBackingBean);
        mav.setViewName("home");
        return mav;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String search(@ModelAttribute("searchForm") StringForm form,
            ModelMap model) {
        model.addAttribute("searchForm", form);
        if (form.getValue() == null || form.getValue().isEmpty()) {
            model.addAttribute("vacations", vacationCRUDService.getAll());
            model.addAttribute("vacationDestinations", vacationCRUDService.getAllDestinations());
        } else {
            model.addAttribute("vacations", vacationCRUDService.getByDestination(form.getValue()));
            model.addAttribute("vacationDestinations", vacationCRUDService.getAllDestinations());
        }
        return "home";
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String renderTest(ModelMap model, Principal principal) {
        User activeUser = (User) ((Authentication) principal).getPrincipal();
        AccountDetailsAdapter account = (AccountDetailsAdapter) activeUser;
        model.addAttribute("username", account.getUsername());
        model.addAttribute("id", account.getId());
        model.addAttribute("message", "Spring Security Custom Form example");
        return "test";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String renderLogin(ModelMap model) {
        model.addAttribute("error", error);
        return "login";
    }
    
    @RequestMapping(value = "/login/error", method = RequestMethod.GET)
    public String renderLoginError(ModelMap model) {
        error = "true";
        return "redirect:/login";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String renderLogout() {
        return "login";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView renderAdd() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("registration", registrationBackingBean);
        mav.setViewName("register");
        return mav;
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addCustomer(@ModelAttribute("registration") RegistrationDTO registration,
            BindingResult result, ModelMap modelMap) {
        registrationValidator.validate(registration, result);
        if (result.hasErrors()) {
            registrationBackingBean = registration;
            modelMap.put(BindingResult.class.getName() + "addedCustomer", result);
            return "register";
        } else {
            registrationService.registerAccount(registration);
            return "redirect:/register/success";
        }
    }
    
    @RequestMapping(value = "/register/success", method = RequestMethod.GET)
    public ModelAndView addSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "customer.add.success");
        mav.setViewName("customer/success");
        return mav;
    }
    
    private CustomerDTO prepareCustomerDTO() {
        CustomerDTO customer = new CustomerDTO();
        customer.setName("John Smith");
        customer.setAddress("Street no.1, City, 12345");
        customer.setPhoneNumber("+420 775 123 456");
        return customer;
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
    
    private VacationDTO prepareVacationDTO() {
        VacationDTO vacation = new VacationDTO();
        vacation.setDestination("Brno");
        vacation.setStartDate(newDate(2012,10,14));
        vacation.setEndDate(newDate(2012,10,15));
        vacation.setPrice(new BigDecimal(2000));
        vacation.setMaxCapacity(1);
        return vacation;
    }
    
    private AccountDTO prepareAccountDTO() {
        AccountDTO account = new AccountDTO();
        account.setName("admin");
        account.setPassword("admin");
        account.setEnabled(true);
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        account.setRoles(roles);
        return account;
    }
    
}
