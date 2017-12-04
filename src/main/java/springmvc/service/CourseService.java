package springmvc.service;

import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Course;

import java.util.List;

public interface CourseService {
    List<Course> list();
    Course getCourse(String course_id);
    List<Course> queryCourse(String course_id, String title, String dept_name, int credits);
    List<String>  getAllCoursesID();
    void AutoAddCourse(int count);
}
