package liu.hope.my_demo_boot.service.impl;

import liu.hope.my_demo_boot.entity.Student;
import liu.hope.my_demo_boot.mapper.StudentMapper;
import liu.hope.my_demo_boot.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public List<Student> getList() {
        return studentMapper.getList();
    }

    @Override
    public int insert(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    public int deleteById(Integer id) {
        return studentMapper.deleteById(id);
    }
}
