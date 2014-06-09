
package cz.muni.fi.pa165.travelagencyclient.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author David Simansky <d.simansky@gmail.com>
 */
@Controller
public class HomeClientController {
    
    
    @RequestMapping("/")
    public String renderHome() {
        return "home";
    }
}
