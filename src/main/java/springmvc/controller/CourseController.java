package springmvc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springmvc.pojo.Course;
import springmvc.pojo.Student;
import springmvc.service.CourseService;
import springmvc.service.StudentService;
import util.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("")
public class CourseController {
    @Autowired
    CourseService courseService;


//    @RequestMapping("/listCourse")
//    public ModelAndView listCourse(){
//        ModelAndView mav = new ModelAndView();
//        List<Course> cs = courseService.list();
//        mav.addObject("cs", cs);
//        mav.setViewName("listCourse");
//        return mav;
//    }

    @RequestMapping("/getCourse")
    public ModelAndView queryCourse(String course_id, String title, String dept_name, String credits, Page page, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int credit;
        if(!credits.equals("")){
            credit = Integer.parseInt(credits);
        }else credit = -631607793;
        PageHelper.offsetPage(page.getStart(),15);
        List<Course> courses= courseService.queryCourse(course_id, title, dept_name, credit);
        int total = (int) new PageInfo<>(courses).getTotal();
        page.caculateLast(total);
        mav.addObject("courses", courses);
        mav.addObject("query_string", request.getQueryString().split("&start=")[0]);
        mav.setViewName("showCourse");
        return mav;
    }

    @RequestMapping("/getAllCourse")
    public ModelAndView getAllCourse(Page page){
        ModelAndView mav = new ModelAndView();
        PageHelper.offsetPage(page.getStart(),15);
        List<Course> courses = courseService.queryCourse("", "", "", -631607793);
        int total = (int) new PageInfo<>(courses).getTotal();
        page.caculateLast(total);
        mav.addObject("courses", courses);
        mav.setViewName("showCourse");
        return mav;
    }



}
