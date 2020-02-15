package liu.hope.my_demo_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/login")
    private String login() {
        return "login";
    }

    @RequestMapping("/logout")
    private String logout() {
        return "logout";
    }

    @PostMapping("/login")
    private String login(String name,String password){
        System.out.println(name);
        System.out.println(password);
        return "friend";
    }

}
