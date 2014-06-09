package cz.muni.fi.pa165.travelagency.web;

import cz.muni.fi.pa165.travelagency.data.dto.CustomerDTO;
import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationOrderDTO;
import cz.muni.fi.pa165.travelagency.service.CustomerCRUDService;
import cz.muni.fi.pa165.travelagency.service.TourCRUDService;
import cz.muni.fi.pa165.travelagency.service.VacationCRUDService;
import cz.muni.fi.pa165.travelagency.service.VacationOrderCRUDService;
import cz.muni.fi.pa165.travelagency.service.adapter.AccountDetailsAdapter;
import cz.muni.fi.pa165.travelagency.web.util.TourSelect;
import cz.muni.fi.pa165.travelagency.web.validator.CustomerValidator;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Michal Jurc
 */
@Controller
@RequestMapping("/order")
public class VacationOrderController {

    @Autowired
    @Qualifier("vacationOrderCRUDService")
    private VacationOrderCRUDService orderCRUDService;
    @Autowired
    @Qualifier("vacationCRUDService")
    private VacationCRUDService vacationCRUDService;
    @Autowired
    @Qualifier("tourCRUDService")
    private TourCRUDService tourCRUDService;
    @Autowired
    @Qualifier("customerValidator")
    private CustomerValidator customerValidator;
    @Autowired
    @Qualifier("customerCRUDService")
    private CustomerCRUDService customerCRUDService;
    private TourSelect tourSelect;
    private VacationOrderDTO order;
    private CustomerDTO customerDTO;
    private VacationDTO vacationDTO;

    @PostConstruct
    public void init() {
        this.order = new VacationOrderDTO();
        order.setVacation(new VacationDTO());
        order.setCustomer(new CustomerDTO());
        this.tourSelect = new TourSelect();
        this.customerDTO = new CustomerDTO();
        this.vacationDTO = new VacationDTO();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView renderList(Principal principal) {
        User activeUser = (User) ((Authentication) principal).getPrincipal();
        AccountDetailsAdapter account = (AccountDetailsAdapter) activeUser;
        ModelAndView mav = new ModelAndView();
        mav.addObject("orders", orderCRUDService.getOrdersByCustomer(account.getId()));
        mav.setViewName("order/list");
        return mav;
    }

    @RequestMapping(value = "/vacation/{id}/1", method = RequestMethod.GET)
    public ModelAndView renderTourSelect(@PathVariable String id, 
            Principal principal) {
        ModelAndView mav = new ModelAndView();
        Long vacationId = Long.parseLong(id);
        User activeUser = (User) ((Authentication) principal).getPrincipal();
        AccountDetailsAdapter account = (AccountDetailsAdapter) activeUser;
        order = new VacationOrderDTO();
        order.setVacation(new VacationDTO());
        order.setCustomer(customerCRUDService.get(account.getId()));
        order.setVacation(vacationCRUDService.get(vacationId));
        List<Long> allTourIds = order.getVacation().getTours();
        List<TourDTO> allTours = new ArrayList<>();
        for (Long tourId : allTourIds) {
            allTours.add(tourCRUDService.get(tourId));
        }
        List<Long> selectedTourIds = order.getTours();
        tourSelect.setSelectedTourIds(selectedTourIds);
        mav.addObject("tours", allTours);
        mav.addObject("selectedTours", tourSelect);
        mav.addObject("vacation", order.getVacation());
        mav.setViewName("order/selecttours");
        return mav;
    }

    @RequestMapping(value = "/vacation/{id}/1", method = RequestMethod.POST)
    public String selectTours(@PathVariable String id,
            @ModelAttribute("selectedTours") TourSelect selectedTours) {
        if (selectedTours == null || selectedTours.getSelectedTourIds() == null) {
            order.setTours(new ArrayList<Long>());
        } else {
            order.setTours(selectedTours.getSelectedTourIds());
        }
        return "redirect:/order/vacation/" + id + "/2";
    }

    @RequestMapping(value = "vacation/{id}/2", method = RequestMethod.GET)
    public ModelAndView renderCustomerForm(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        List<TourDTO> tours = new ArrayList<>();
        for (Long tourId : order.getTours()) {
            tours.add(tourCRUDService.get(tourId));
        }
        mav.addObject("tours", tours);
        mav.addObject("vacation", order.getVacation());
        mav.addObject("addedCustomer", order.getCustomer());
        mav.setViewName("order/customerform");
        return mav;
    }

    @RequestMapping(value = "vacation/{id}/2", method = RequestMethod.POST)
    public String selectCustomer(@PathVariable String id) {
        order.setReservations(1);
        orderCRUDService.create(order);
        return "redirect:/order/vacation/success";
    }

    @RequestMapping(value = "vacation/success", method = RequestMethod.GET)
    public ModelAndView renderSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "order.add.success");
        mav.setViewName("order/success");
        return mav;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ModelAndView renderDetail(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long orderId = Long.parseLong(id);
        order = orderCRUDService.get(orderId);
        customerDTO = order.getCustomer();
        vacationDTO = order.getVacation();
        List<TourDTO> tourDTOs = getTours(order.getTours());
        mav.addObject("order", order);
        mav.addObject("customer", customerDTO);
        mav.addObject("vacation", vacationDTO);
        mav.addObject("tours", tourDTOs);
        mav.setViewName("order/detail");
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView renderEdit(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long orderId = Long.parseLong(id);
        order = orderCRUDService.get(orderId);
        customerDTO = order.getCustomer();
        vacationDTO = order.getVacation();
        List<TourDTO> tourDTOs = getTours(order.getTours());
        mav.addObject("order", order);
        mav.addObject("customer", customerDTO);
        mav.addObject("vacation", vacationDTO);
        mav.addObject("tours", tourDTOs);
        mav.setViewName("order/edit");
        return mav;
    }

    private List<TourDTO> getTours(List<Long> listOfId) {
        List<TourDTO> result = new ArrayList<>();
        for (Long id : listOfId) {
            TourDTO tour = tourCRUDService.get(id);
            result.add(tour);
        }
        return result;
    }

    @RequestMapping(value = "/tour/reselect/{id}", method = RequestMethod.GET)
    public ModelAndView renderReselectTours(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long orderId = Long.parseLong(id);
        order = orderCRUDService.get(orderId);
        List<Long> allTourIds = order.getVacation().getTours();
        List<TourDTO> allTours = new ArrayList<>();
        for (Long tourId : allTourIds) {
            allTours.add(tourCRUDService.get(tourId));
        }
        List<Long> selectedTourIds = order.getTours();
        tourSelect.setSelectedTourIds(selectedTourIds);
        mav.addObject("tours", allTours);
        mav.addObject("selectedTours", tourSelect);
        mav.addObject("vacation", order.getVacation());
        mav.setViewName("order/selecttours");
        return mav;
    }

    @RequestMapping(value = "/tour/reselect/{id}", method = RequestMethod.POST)
    public String reselectTours(@PathVariable String id,
            @ModelAttribute("selectedTours") TourSelect selectedTours) {
        Long orderId = Long.parseLong(id);
        order = orderCRUDService.get(orderId);
        if (selectedTours == null || selectedTours.getSelectedTourIds() == null) {
            order.setTours(new ArrayList<Long>());
        } else {
            order.setTours(selectedTours.getSelectedTourIds());
        }
        orderCRUDService.update(order);
        return "redirect:/order/get/" + id;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView renderDelete(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long deletedVacationId = Long.parseLong(id);
        VacationOrderDTO deleteOrder = orderCRUDService.get(deletedVacationId);
        mav.addObject("deletedOrder", deleteOrder);
        mav.setViewName("order/delete");
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteVacation(@PathVariable String id) {
        Long deletedVacationId = Long.parseLong(id);
        VacationOrderDTO deletedOrder = orderCRUDService.get(deletedVacationId);
        orderCRUDService.delete(deletedOrder);
        return "redirect:/order/delete/success";
    }

    @RequestMapping(value = "/delete/success", method = RequestMethod.GET)
    public ModelAndView renderDeleteSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "order.delete.success");
        mav.setViewName("order/success");
        return mav;
    }
}
