package liu.hope.redis6379.controller;

import liu.hope.redis6379.service.UserService;
import liu.hope.redis6379.util.RedisUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hope
 * @version 1.0
 * TODO:
 * @createTime 2019/12/10 10:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/put/{key}/{value}")
    public String put(@PathVariable("key") String key, @PathVariable("value") String value){
        String result = userService.put(key, value);
        return result;
    }

    @RequestMapping("/able/{key}/{value}")
    public String able(@PathVariable("key") String key, @PathVariable("value") String value){
        String result = userService.able(key, value);
        return result;
    }

    @RequestMapping("/evict/{key}/{value}")
    public String evict(@PathVariable("key") String key, @PathVariable("value") String value){
        String result = userService.evict(key, value);
        return result;
    }

    @RequestMapping("/get/{key}")
    public String get(@PathVariable("key") String key){
        Object aa = redisUtil.get("USERCACHE::" + key);
        return aa.toString();
    }

}
