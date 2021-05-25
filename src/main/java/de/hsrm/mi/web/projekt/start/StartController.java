package de.hsrm.mi.web.projekt.start;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("/")
    public String getStartLoginPage() {
        return "start/start";
    }

}
