package springmvc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springmvc.pojo.Course;
import springmvc.pojo.Section;
import springmvc.pojo.Student;
import springmvc.pojo.Takes;
import springmvc.service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseManageController {


    @Autowired
    TakesService takesService;

    @Autowired
    CourseService courseService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    TeachesService teachesService;

    @Autowired
    SectionService sectionService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/manageCourse")
    public ModelAndView courseManager(HttpSession session){
        ModelAndView mav = new ModelAndView();
        Student student = studentService.getStudent(session.getAttribute("stuID").toString());
        mav.addObject("student", student);
        List<Takes> takes = takesService.studentTakes(session.getAttribute("stuID").toString());
        List<Course> cs = new ArrayList<>();
        for(Takes t : takes){
            Course course = courseService.getCourse(t.getCourse_id());
            Section sec = sectionService.getSection(t.getCourse_id(), t.getSec_id(), t.getSemester(), t.getYear());
            course.setInstructor(instructorService.get(teachesService.getTeacherID(sec.getCourse_id(), sec.getSec_id(), sec.getSemester(), sec.getYear())).getName());
            course.setClassroom(sec.getBuilding()+"  "+sec.getRoom_number());
            cs.add(course);
        }
        mav.addObject("courses", cs);
        mav.setViewName("manageCourse");
        return mav;
    }
}
