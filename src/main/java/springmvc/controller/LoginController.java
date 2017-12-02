package springmvc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springmvc.mapper.CourseMapper;
import springmvc.pojo.*;
import springmvc.service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    StudentService studentService;

    @Autowired
    TakesService takesService;

    @Autowired
    CourseService courseService;

    @Autowired
    InstructorService instructorService;


    @Autowired
    SectionService sectionService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/verifyLogin", method = RequestMethod.POST)
    public ModelAndView login(String stuId, String stuName, HttpSession session){
        ModelAndView mav = new ModelAndView();
        if(stuId == null || stuName == null || stuId.equals("") || stuName.equals("")){
            mav.setViewName("login");
            return mav;
        }
        Student stu = studentService.getStudent(stuId);
        if(stu != null && stu.getName().equals(stuName)){
            session.setAttribute("stuID", stuId);
            session.setAttribute("stuName", stuName);
            if(session.getAttribute("goto") == null || session.getAttribute("goto").toString().toLowerCase().contains("login"))
                return new ModelAndView("redirect:/manageCourse");
            else return new ModelAndView("redirect:"+session.getAttribute("goto").toString());
        }else{
            mav.addObject("info", "Wrong ID or Name!");
            mav.setViewName("login");
        }
        return mav;
    }
}
