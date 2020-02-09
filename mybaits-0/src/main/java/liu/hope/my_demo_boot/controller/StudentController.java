package liu.hope.my_demo_boot.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import liu.hope.my_demo_boot.entity.Student;
import liu.hope.my_demo_boot.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping(value="/student")     // 通过这里配置使下面的映射都在/students下，可去除
public class StudentController {

    private Logger logger = LogManager.getLogger(getClass());

    @Resource
    private StudentService studentService;

    static Map<Integer, Student> students = Collections.synchronizedMap(new HashMap<Integer, Student>());

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value={"/getStudentList"}, method= RequestMethod.GET)
    public List<Student> getStudentList() {
        return studentService.getList();
    }

    @ApiOperation(value="创建用户", notes="根据Student对象创建用户")
    @ApiImplicitParam(name = "student", value = "用户详细实体Student", required = true, dataType = "Student")
    @RequestMapping(value="/post", method=RequestMethod.POST)
    public String postStudent(@RequestBody Student student) {
        int i = studentService.insert(student);
        return i == 1 ? "success" : "failed";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, defaultValue = "0")
    @RequestMapping(value="/getStudent/{id}", method=RequestMethod.GET)
    public Student getStudent(@PathVariable("id") Integer id) {
        return studentService.getById(id);
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的Student信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, defaultValue = "0"),
            @ApiImplicitParam(name = "Student", value = "用户详细实体Student", required = true, dataType = "Student")
    })
    @RequestMapping(value="/put/{id}", method=RequestMethod.PUT)
    public String putStudent(@PathVariable("id") Integer id, @RequestBody Student student) {
        Student u = students.get(id);
        u.setName(student.getName());
        u.setAge(student.getAge());
        students.put(id, u);
        return "success";
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, defaultValue = "1")
    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public String deleteStudent(@PathVariable("id") Integer id) {
        int i = studentService.deleteById(id);
        return i == 1 ? "success" : "failed";
    }

}