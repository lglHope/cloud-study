package liu.hope.mongo27017.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MongoControllerTest {

    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    public void insert() {
        List<Map<String,String>> names = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<>(1);
            map.put("name", "xiaoming" + i);
            names.add(map);
        }
        mongoTemplate.insert(names, "names");
    }

    @Test
    public void findAll() {
        List<Map> mapList = mongoTemplate.findAll(Map.class);
        mapList.forEach(map -> System.out.println(map.get("name")));
    }
}