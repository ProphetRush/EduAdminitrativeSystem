package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.CourseMapper;
import springmvc.mapper.InstructorMapper;
import springmvc.mapper.SectionMapper;
import springmvc.pojo.Course;
import springmvc.pojo.Section;
import springmvc.service.CourseService;
import springmvc.service.InstructorService;
import springmvc.service.SectionService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    SectionMapper sectionMapper;
    @Autowired
    InstructorMapper instructorMapper;


    public List<Course> list(){
        return courseMapper.list();
    };

    public Course getCourse(String id){
        return courseMapper.get(id);
    }

    public List<Course> queryCourse(String course_id, String title, String dept_name, int credits) {
        return courseMapper.query(course_id, title, dept_name, credits);
    }

    @Override
    public List<String> getAllCoursesID() {
        return courseMapper.getAllCoursesID();
    }


}
