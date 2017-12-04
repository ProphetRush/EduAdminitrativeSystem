package springmvc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springmvc.pojo.Course;
import springmvc.service.CourseService;
import springmvc.service.InstructorService;
import springmvc.service.SectionService;
import util.Page;
import util.Resp;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    SectionService sectionService;
    @Autowired
    InstructorService instructorService;


//    @RequestMapping("/listCourse")
//    public ModelAndView listCourse(){
//        ModelAndView mav = new ModelAndView();
//        List<Course> cs = courseService.list();
//        mav.addObject("cs", cs);
//        mav.setViewName("listCourse");
//        return mav;
//    }

    @RequestMapping("/getCourse")
    public @ResponseBody String queryCourse(String course_id, String title, String dept_name, String credits, Page page, HttpServletRequest request) {
        Resp resp = new Resp();
        int credit;
        if(!credits.equals("")){
            credit = Integer.parseInt(credits);
        }else credit = -631607793;
//        PageHelper.offsetPage(page.getStart(),15);
        try{
            List<Course> courses= courseService.queryCourse(course_id, title, dept_name, credit);
            resp.setData(courses);
            return JSON.toJSONString(resp);
        }catch (Exception e){
            resp.setStatus("Failed");
            resp.setMassage(e.toString());
            return  JSONArray.toJSONString(resp);
        }

//        int total = (int) new PageInfo<>(courses).getTotal();
//        page.caculateLast(total);
//        mav.addObject("courses", courses);
//        mav.addObject("query_string", request.getQueryString().split("&start=")[0]);
//        mav.setViewName("showCourse");
//        return mav;
    }

    @RequestMapping("/getAllCourse")
    public @ResponseBody String getAllCourse(){
        Resp resp = new Resp();
        try{
            List<Course> courses = courseService.queryCourse("", "", "", -631607793);
            resp.setData(courses);
            return JSON.toJSONString(resp);
        }catch (Exception e){
            resp.setStatus("Failed");
            resp.setMassage(e.toString());
            return JSON.toJSONString(resp);
        }


    }

    @RequestMapping("/autoAddCourse")
    public @ResponseBody Resp autoAddCourse(int count){

        try{
            courseService.AutoAddCourse(count);
            Resp resp = new Resp();
            resp.setStatus("Success");
            return resp;
        }catch (Exception e){
            Resp resp = new Resp();
            resp.setStatus("Failed");
            resp.setMassage(e.toString());
            return resp;
        }

    }



}
