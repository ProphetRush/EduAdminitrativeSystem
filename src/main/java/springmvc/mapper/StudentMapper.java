package springmvc.mapper;

import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Student;

import java.util.List;

public interface StudentMapper {
    public void add(Student student);
    public void delete(Student student);
    public Student get(String id);
    public List<Student> list();
    public void update(Student student);
    public int count();
    public List<Student> getAllStudents();
    public List<String> getAllStudentID();
}
