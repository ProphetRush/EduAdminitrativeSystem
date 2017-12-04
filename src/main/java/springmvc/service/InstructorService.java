package springmvc.service;

import springmvc.pojo.Instructor;

import java.util.List;

public interface InstructorService {
    Instructor get(String ID);
    String getInstructorID(String instructor_name);
    List<String> getAllInstructorIDByDept(String dept_name);
}
