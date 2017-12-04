package springmvc.mapper;
import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Course;

import java.util.List;

public interface CourseMapper {
    public void add(Course course);
    public void delete(String id);
    public Course get(String id);
    public List<Course> list();
    public void update(Course course);
    public int count();
    public List<Course> query(@Param("course_id") String course_id, @Param("title") String title, @Param("dept_name") String dept_name, @Param("credits") int credits);
    public List<String> getAllCoursesID();
    public void AddCourse(@Param("cid") String course_id, @Param("sid") String sec_id, @Param("semester") String semester
            , @Param("year") int year, @Param("building") String building, @Param("rn") String room_number, @Param("tid") String time_slot_id, @Param("iid") String instructor_id);
    public String getDeptNameByCourseID(String course_id);
}
