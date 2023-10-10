package s.s.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeyController {
@RequestMapping("/")
public String sysOut(){
    return "Hello world";
}
}
