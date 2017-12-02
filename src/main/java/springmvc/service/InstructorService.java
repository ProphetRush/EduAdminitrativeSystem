package springmvc.service;

import springmvc.pojo.Instructor;

public interface InstructorService {
    Instructor get(String ID);
    String getInstructorID(String instructor_name);
}
