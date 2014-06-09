package cz.muni.fi.pa165.travelagency.web;

import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import cz.muni.fi.pa165.travelagency.data.dto.VacationDTO;
import cz.muni.fi.pa165.travelagency.service.TourCRUDService;
import cz.muni.fi.pa165.travelagency.service.VacationCRUDService;
import cz.muni.fi.pa165.travelagency.web.util.TourSelect;
import cz.muni.fi.pa165.travelagency.web.validator.VacationValidator;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/vacation")
public class VacationController {
    
    private VacationDTO addedVacationBackingBean;
    private VacationDTO editedVacationsBackingBeans;
    private TourSelect tourSelect;
    
    @Autowired
    @Qualifier("vacationCRUDService")
    private VacationCRUDService vacationCRUDService;
    
    @Autowired
    @Qualifier("tourCRUDService")
    private TourCRUDService tourCRUDService;

    @Autowired
    @Qualifier("vacationValidator")
    private VacationValidator vacationValidator;
    
    @PostConstruct
    public void init() {
        addedVacationBackingBean = new VacationDTO();
        editedVacationsBackingBeans = new VacationDTO();
        tourSelect = new TourSelect();
    }
    
    @InitBinder
    public void bind(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            
            @Override
            public void setAsText(String value) {
                try {
                    setValue(new SimpleDateFormat("dd/MM/yyyy").parse(value));
                } catch (ParseException ex) {
                    setValue(null);
                }
            }
            
            @Override
            public String getAsText() {
                if (getValue() == null) {
                    return "dd/MM/yyyy";
                }
                return new SimpleDateFormat("dd/MM/yyyy").format((Date) getValue());
            }
            
        });
        
        binder.registerCustomEditor(int.class, new PropertyEditorSupport() {
           
            @Override
            public void setAsText(String value) {
                try {
                    setValue(Integer.parseInt(value));
                } catch (NumberFormatException ex) {
                    setValue(0);
                }
            }
            
            @Override
            public String getAsText() {
                if (getValue() == null) {
                    return "";
                }
                return getValue().toString();
            }
            
        });
    }
        
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView renderList() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("vacations", vacationCRUDService.getAll());
        mav.setViewName("vacation/list");
        return mav;
    }
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ModelAndView renderDetail(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        VacationDTO vacationDTO = vacationCRUDService.get(Long.parseLong(id));
        mav.addObject("vacation", vacationDTO);
        List<TourDTO> tours = new ArrayList<>();
        List<Long> selectedTourIds = vacationCRUDService.get(vacationDTO.getId()).getTours();
        for (Long tourId : selectedTourIds) {
            tours.add(tourCRUDService.get(tourId));
        }
        mav.addObject("tours", tours);
        mav.setViewName("vacation/detail");
        return mav;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView renderAdd() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("addedVacation", addedVacationBackingBean);
        mav.setViewName("vacation/add");
        return mav;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addVacation(@ModelAttribute("addedVacation") VacationDTO addedVacation,
            BindingResult result, ModelMap modelMap) {
        vacationValidator.validate(addedVacation, result);
        if (result.hasErrors()) {
            addedVacationBackingBean = addedVacation;
            modelMap.put(BindingResult.class.getName() + "addedVacation", result);
            return "add";
        } else {
            vacationCRUDService.create(addedVacation);
            return "redirect:/vacation/add/success";
        }
    }
    
    @RequestMapping(value = "/add/success", method = RequestMethod.GET)
    public ModelAndView addSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "vacation.add.success");
        mav.setViewName("vacation/success");
        return mav;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView renderEdit(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long editedVacationId = Long.parseLong(id);
        editedVacationsBackingBeans = vacationCRUDService.get(editedVacationId);
        mav.addObject("editedVacation", editedVacationsBackingBeans);
        mav.setViewName("vacation/edit");
        return mav;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editVacation(@ModelAttribute("editedVacation") VacationDTO editedVacation,
            @PathVariable String id, BindingResult result, ModelMap modelMap) {
        vacationValidator.validate(editedVacation, result);
        if (result.hasErrors()) {
            modelMap.put("editedVacation", editedVacation);
            modelMap.put(BindingResult.class.getName() + "editedVacation", result);
            return "vacation/edit";
        } else {
            vacationCRUDService.update(editedVacation);
            return "redirect:/vacation/edit/success";
        }
    }
    
    @RequestMapping(value = "/edit/success", method = RequestMethod.GET)
    public ModelAndView editSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "vacation.edit.success");
        mav.setViewName("vacation/success");
        return mav;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView renderDelete(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long deletedVacationId = Long.parseLong(id);
        VacationDTO deletedVacation = vacationCRUDService.get(deletedVacationId);
        mav.addObject("deletedVacation", deletedVacation);
        mav.setViewName("vacation/delete");
        return mav;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteVacation(@PathVariable String id) {
        Long deletedVacationId = Long.parseLong(id);
        VacationDTO deletedVacation = vacationCRUDService.get(deletedVacationId);
        vacationCRUDService.delete(deletedVacation);
        return "redirect:/vacation/delete/success";
    }
    
    @RequestMapping(value = "/delete/success", method = RequestMethod.GET)
    public ModelAndView deleteSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "vacation.delete.success");
        mav.setViewName("vacation/success");
        return mav;
    }
    
    @RequestMapping(value = "/tours/{id}", method = RequestMethod.GET)
    public ModelAndView renderTours(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long vacationId = Long.parseLong(id);
        List<TourDTO> tours = new ArrayList<>();
        for (Long tourId : vacationCRUDService.get(vacationId).getTours()) {
            tours.add(tourCRUDService.get(tourId));
        }
        mav.addObject("tours", tours);
        mav.addObject("vacationId", id);
        mav.setViewName("vacation/tours");
        return mav;
    }
    
    @RequestMapping(value = "/tours/{id}/select", method = RequestMethod.GET)
    public ModelAndView renderSelectTours(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long vacationId = Long.parseLong(id);
        List<TourDTO> allTours = tourCRUDService.getAll();
        List<Long> selectedTourIds = vacationCRUDService.get(vacationId).getTours();
        tourSelect.setSelectedTourIds(selectedTourIds);
        mav.addObject("tours", allTours);
        mav.addObject("selectedTours", tourSelect);
        mav.setViewName("vacation/selecttours");
        return mav;
    }
    
    @RequestMapping(value = "/tours/{id}/select", method = RequestMethod.POST)
    public String renderSelectTours(@PathVariable String id,
            @ModelAttribute("selectedTours") TourSelect selectedTours) {
        Long vacationId = Long.parseLong(id);
        VacationDTO vacation = vacationCRUDService.get(vacationId);
        if (selectedTours == null || selectedTours.getSelectedTourIds() == null) {
            vacation.setTours(new ArrayList<Long>());
        } else {
            vacation.setTours(selectedTours.getSelectedTourIds());
        }
        vacationCRUDService.update(vacation);
        return "redirect:/vacation/tours/" + id;
    }
    
}
