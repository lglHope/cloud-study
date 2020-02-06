package liu.hope.my_demo_boot.mapper;

import liu.hope.my_demo_boot.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student where id = #{id}")
    Student getById(Integer id);

    @Select("select * from student")
    List<Student> getList();

    @Insert("insert into student(id,name,age,score,birthday,cid) values (#{id},#{name},#{age},#{score},#{birthday},#{cid)}")
    int insert(Student student);

    @Delete("delete from student where id = #{id}")
    int deleteById(Integer id);
}
