package cz.muni.fi.pa165.travelagency.web;

import cz.muni.fi.pa165.travelagency.data.dto.TourDTO;
import cz.muni.fi.pa165.travelagency.service.TourCRUDService;
import cz.muni.fi.pa165.travelagency.web.validator.TourValidator;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaSystemException;
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
@RequestMapping("/tour")
public class TourController {

    private TourDTO addedTourBackingBean;
    private TourDTO editedTourBackingBean;
    @Autowired
    @Qualifier("tourCRUDService")
    private TourCRUDService tourCRUDService;
    @Autowired
    @Qualifier("tourValidator")
    private TourValidator tourValidator;

    @PostConstruct
    public void init() {
        addedTourBackingBean = new TourDTO();
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
        mav.addObject("tours", tourCRUDService.getAll());
        mav.setViewName("tour/list");
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView renderAdd() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("addedTour", addedTourBackingBean);
        mav.setViewName("tour/add");
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTour(@ModelAttribute("addedTour") TourDTO tourDTO,
            BindingResult result, ModelMap modelMap) {
        tourValidator.validate(tourDTO, result);
        if (result.hasErrors()) {
            addedTourBackingBean = tourDTO;
            modelMap.put(BindingResult.class.getName() + "addedTour", result);
            return "add";
        } else {
            tourCRUDService.create(tourDTO);
            return "redirect:/tour/add/success";
        }
    }

    @RequestMapping(value = "/add/success", method = RequestMethod.GET)
    public ModelAndView addSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "tour.add.success");
        mav.setViewName("tour/success");
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView renderEdit(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long editedTourId = Long.parseLong(id);
        editedTourBackingBean = tourCRUDService.get(editedTourId);
        mav.addObject("editedTour", editedTourBackingBean);
        mav.setViewName("tour/edit");
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editTour(@ModelAttribute("editedTour") TourDTO editedTour,
            @PathVariable String id, BindingResult result, ModelMap modelMap) {
        tourValidator.validate(editedTour, result);
        if (result.hasErrors()) {
            modelMap.put("editedTour", editedTour);
            modelMap.put(BindingResult.class.getName() + "editedTour", result);
            return "tour/edit";
        } else {
            tourCRUDService.update(editedTour);
            return "redirect:/tour/edit/success";
        }
    }

    @RequestMapping(value = "/edit/success", method = RequestMethod.GET)
    public ModelAndView editSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "tour.edit.success");
        mav.setViewName("tour/success");
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView renderDelete(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        Long deletedTourId = Long.parseLong(id);
        TourDTO deletedTour = tourCRUDService.get(deletedTourId);
        mav.addObject("deletedTour", deletedTour);
        mav.setViewName("tour/delete");
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteTour(@PathVariable String id) {
        Long deletedTourId = Long.parseLong(id);
        TourDTO deletedTour = tourCRUDService.get(deletedTourId);
        try {
            tourCRUDService.delete(deletedTour);
        } catch (JpaSystemException ex) {
            return "redirect:/tour/delete/error";
        }
        return "redirect:/tour/delete/success";
    }

    @RequestMapping(value = "/delete/success", method = RequestMethod.GET)
    public ModelAndView deleteSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "tour.delete.success");
        mav.setViewName("tour/success");
        return mav;
    }

    @RequestMapping(value = "/delete/error", method = RequestMethod.GET)
    public ModelAndView deleteError() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("successMessage", "tour.delete.error");
        mav.setViewName("tour/success");
        return mav;
    }
}
