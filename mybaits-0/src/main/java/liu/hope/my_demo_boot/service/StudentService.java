package liu.hope.my_demo_boot.service;

import liu.hope.my_demo_boot.entity.Student;

import java.util.List;

public interface StudentService {

    Student getById(Integer id);

    List<Student> getList();

    int insert(Student student);

    int deleteById(Integer id);
}
