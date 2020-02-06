package liu.hope.my_demo_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FriendController {

    @GetMapping("getMyFriend")
    public String myFriend(ModelMap modelMap){
        modelMap.addAttribute("path", "http://localhost:8080/images/friend.png");
        return "friend";
    }
}
