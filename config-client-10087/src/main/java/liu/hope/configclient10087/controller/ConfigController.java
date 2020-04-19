package liu.hope.configclient10087.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/config")
public class ConfigController {

    @Value("${user.username}")
    public String name;

    @RequestMapping("getName")
    @ResponseBody
    public String getName() {
        return name;
    }
}
