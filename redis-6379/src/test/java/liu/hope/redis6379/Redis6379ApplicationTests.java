package liu.hope.redis6379;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Redis6379ApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        // 一次操作一个事务
        redisTemplate.opsForValue().set("name", "小胡");

        // 多次操作一个事务
        String name = (String) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                redisTemplate.opsForValue().set("name2","小胡");
                String name2 = (String) redisTemplate.opsForValue().get("name2");
                return name2;
            }
        });
        System.out.println("name2:" + name);
    }

}
