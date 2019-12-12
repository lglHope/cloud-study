package liu.hope.dubbo20880.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import liu.hope.zookeeper2181.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private IUserService iUserService;

    @RequestMapping("/get")
    public String get(){
        return iUserService.getOne();
    }
}
