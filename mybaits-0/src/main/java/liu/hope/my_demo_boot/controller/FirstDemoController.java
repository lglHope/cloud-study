package liu.hope.my_demo_boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstDemoController {

    @GetMapping("/getHello")
    public String getHello(){
        return "Hello World";
    }

    @GetMapping("getName/{name}")
    public String getName(@PathVariable("name") String name){
        return "Hello " + name;
    }
}
