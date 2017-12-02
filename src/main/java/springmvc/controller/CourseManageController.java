package springmvc.controller;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springmvc.pojo.*;
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
        int creditSelected = 0;
        for(Takes t : takes){
            Course course = courseService.getCourse(t.getCourse_id());
            Section sec = sectionService.getSection(t.getCourse_id(), t.getSec_id(), t.getSemester(), t.getYear());
            course.setInstructor(instructorService.get(sec.getInstructor_id()).getName());
            course.setGrade(t.getGrade());
            cs.add(course);
            creditSelected+=course.getCredits();
        }
        mav.addObject("courses", cs);
        mav.addObject("creditSum", creditSelected);
        mav.setViewName("manageCourse");
        return mav;
    }

    @RequestMapping("queryCourse")
    public ModelAndView queryCourse(HttpSession session, String course_id, String title, String credits, String dept_name, String instructor_id, String time){
        ModelAndView mav = new ModelAndView();
        int credit;
        if(credits!=null && !credits.equals("")){
            credit = Integer.parseInt(credits);
        }else credit = -631607793;
        List<Section> secs = sectionService.querySection(course_id, title,dept_name,credit,instructor_id, time);
        for(Section s: secs){
            s.setInstructor_name(instructorService.get(s.getInstructor_id()).getName());
        }
        mav.addObject("sections", JSON.toJSONString(secs));
        mav.setViewName("showResult");
        return mav;


    }
}
