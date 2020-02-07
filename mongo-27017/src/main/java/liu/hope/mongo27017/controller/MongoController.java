package liu.hope.mongo27017.controller;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * Mongodb操作示范
 */
@RestController
@RequestMapping("/mongo")
public class MongoController {

    public static final String SUCCESS = "SUCCESS";

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 新增单个对象
     * @param name
     * @return
     */
    @GetMapping("/insert/{name}")
    public String insert(@PathVariable String name){
        Map<String, String> map = new HashMap<>(1);
        map.put("name", name);
        mongoTemplate.insert(map, "name");
        return SUCCESS;
    }

    /**
     * 新增示范例子
     * @return
     */
    @GetMapping("/insert/all")
    public String insert(){
        List<Map<String,String>> names = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<>(1);
            map.put("name", "xiaoming" + i);
            names.add(map);
        }
        mongoTemplate.insert(names, "names");
        return SUCCESS;
    }

    /**
     * 更新数据，有则更新，没有就新增
     * @param name
     * @return
     */
    @GetMapping("/update/{name}")
    public String update(@PathVariable String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("xiaoming0"));
        Update update = new Update();
        // 更新文档中的属性
        update.set("name", name);
        // 如果要更新内嵌文档数据，要使用
        // update.set("user.$.name", name);
        // 如果要更新整个文档的数据，要使用addToSet()
        mongoTemplate.upsert(query, update, Map.class, "names");
        return SUCCESS;
    }

    @GetMapping("/findAll/{key}")
    public List<HashMap> findAll(@PathVariable String key){
        return mongoTemplate.findAll(HashMap.class, key);
    }

    @GetMapping("/findOne/{key}")
    public Map findOne(@PathVariable String key){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(key));
        return mongoTemplate.findOne(query, HashMap.class, "names");
    }

    /**
     * 更新数据，有则更新，没有就新增
     * @param name
     * @return
     */
    @GetMapping("/remove/{name}")
    public String remove(@PathVariable String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Update update = new Update();
        // 删除文档
        update.unset("name");
        // 如果要更新内嵌文档数据，要使用
        // update.set("user.$");
        mongoTemplate.updateFirst(query, update, Map.class, "names");
        return SUCCESS;
    }
}
