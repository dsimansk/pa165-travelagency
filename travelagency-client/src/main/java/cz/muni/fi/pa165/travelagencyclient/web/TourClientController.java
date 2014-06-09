package cz.muni.fi.pa165.travelagencyclient.web;

import cz.muni.fi.pa165.travelagencyclient.web.dto.TourDTO;
import cz.muni.fi.pa165.travelagencyclient.web.dto.TourDTOResponse;
import cz.muni.fi.pa165.travelagencyclient.web.validator.TourValidator;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author David Simansky
 */
@Controller
@RequestMapping("/tour")
public class TourClientController {

    private TourDTO addedTourBackingBean;
    private TourDTO editedTourBackingBean;
    private String remoteHost = "http://localhost:8080/pa165/rest/";
    
    @Autowired
    private RestTemplate rest;
    
    
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getTour(@PathVariable String id) {
        TourDTO tour = getTourRemote(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("tour", tour);
        mav.setViewName("tour/detail");
        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getTours() {
        String url = remoteHost + "tour/";
        TourDTOResponse tr = rest.getForObject(url, TourDTOResponse.class);
        List<TourDTO> tours = tr.getListData();
        ModelAndView mav = new ModelAndView();
        mav.addObject("tours", tours);
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
        String url = remoteHost + "tour/";
        if (result.hasErrors()) {
            addedTourBackingBean = tourDTO;
            modelMap.put(BindingResult.class.getName() + "addedTour", result);
            return "/tour/add";
        } else {
            rest.postForObject(url, tourDTO, TourDTO.class);
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
        editedTourBackingBean = getTourRemote(id);
        mav.addObject("editedTour", editedTourBackingBean);
        mav.setViewName("tour/edit");
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editTour(@ModelAttribute("editedTour") TourDTO editedTour,
            @PathVariable String id, BindingResult result, ModelMap modelMap) {
        tourValidator.validate(editedTour, result);
        String url = remoteHost + "tour/";
        if (result.hasErrors()) {
            modelMap.put("editedTour", editedTour);
            modelMap.put(BindingResult.class.getName() + "editedTour", result);
            return "tour/edit";
        } else {
            rest.put(url, editedTour);
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
        TourDTO deletedTour = getTourRemote(id);
        mav.addObject("deletedTour", deletedTour);
        mav.setViewName("tour/delete");
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteTour(@PathVariable String id) {
        String url = remoteHost + "tour/" + id;
        rest.delete(url);
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

    private TourDTO getTourRemote(String id) throws RestClientException {
        String url = remoteHost + "tour/" + id;
        TourDTOResponse node = rest.getForObject(url, TourDTOResponse.class);
        TourDTO tour = node.getTourDTO();
        return tour;
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
        mav.setViewName("tour/error");

        return mav;
    }
}
