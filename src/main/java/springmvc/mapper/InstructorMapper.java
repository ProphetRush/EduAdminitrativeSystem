package springmvc.mapper;


import springmvc.pojo.Instructor;

import java.util.List;

public interface InstructorMapper {
    public void add(Instructor instructor);
    public void delete(Instructor instructor);
    public Instructor get(String ID);
    public void update(Instructor instructor);
    public int count();
    public String getInstructorID(String instructor_name);
    public List<String> getAllInstructorIDByDept(String dept_name);
}
