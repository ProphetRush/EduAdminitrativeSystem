package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.CourseMapper;
import springmvc.pojo.Course;
import springmvc.service.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    public List<Course> list(){
        return courseMapper.list();
    };

    public Course getCourse(String id){
        return courseMapper.get(id);
    }

    public List<Course> queryCourse(String course_id, String title, String dept_name, int credits) {
        return courseMapper.query(course_id, title, dept_name, credits);
    }
}
