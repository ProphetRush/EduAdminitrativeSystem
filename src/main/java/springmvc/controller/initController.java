package springmvc.controller;

import annotations.Permission_All;
import annotations.Permission_Root;
import annotations.Permission_Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springmvc.mapper.*;
import springmvc.pojo.*;
import springmvc.service.*;
import util.Resp;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@Controller
public class initController {
    @Autowired
    TakesService takesService;

    @Autowired
    CourseService courseService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TakesMapper takesMapper;


    @Autowired
    SectionService sectionService;

    @Autowired
    StudentService studentService;
    @Autowired
    CourseMapper courseMapper;

    @Autowired
    InstructorMapper instructorMapper;
    @Autowired
    UserMapper userMapper;


    @Permission_Root
    @RequestMapping("/autoAddSections")
    public @ResponseBody
    String autoAddCourse(int count){
        Resp resp = new Resp();
        try{
            List<Section> sectionsAdded = sectionService.AutoAddSections(count);
            resp.setData(sectionsAdded);
            return resp.toJSON();
        }catch (Exception e){
            resp.setFailed(e);
            return resp.toJSON();
        }

    }

    @Permission_Root
    @RequestMapping("/initCreditLeft")
    public @ResponseBody String initCreditLeft(){
        Resp resp = new Resp();
        try{
            List<Student> students = studentMapper.getAllStudents();
            for(Student stu: students) {
                int credit_used = 0;
                List<Takes> takes = takesService.studentTakes(stu.getID());
                for(Takes t: takes){
                    credit_used += courseService.getCourse(t.getCourse_id()).getCredits();
                }
                if(stu.getTot_cred()-credit_used>0) stu.setCredit_left(stu.getTot_cred()-credit_used);
                else stu.setCredit_left(0);
                studentMapper.update(stu);
            }
            resp.setMessage("Successfully update credit_left field of student table!");
            resp.setStatus("Success");
            return resp.toJSON();

        }catch (Exception e){
            resp.setFailed(e);
            return resp.toJSON();
        }
    }

    @Permission_Root
    @RequestMapping("initUsers")
    public @ResponseBody String initUsers(){
        Resp resp = new Resp();
        try{
            List<Student> students = studentMapper.getAllStudents();
            List<User> users = new ArrayList<>();
            for(Student s: students){
                User stu = new User(s);
                users.add(stu);
            }
            List<Instructor> instructors = instructorMapper.getAllInstructors();
            for(Instructor s: instructors){
                User ins = new User(s);
                users.add(ins);
            }
            userMapper.initUsers(users);
            resp.setData(users);
            return resp.toJSON();
        }catch (Exception e){
            resp.setFailed(e);
            return resp.toJSON();
        }
    }



    //    @RequestMapping("/initCreditLeft")
//    public @ResponseBody String initCreditLeft(){
//        Resp resp = new Resp();
//        try{
//            List<Student> students = studentMapper.getAllStudents();
//            for(Student stu: students) {
//                int credit_used = 0;
//                List<Takes> takes = takesService.studentTakes(stu.getID());
//                for(Takes t: takes){
//                    credit_used += courseService.getCourse(t.getCourse_id()).getCredits();
//                }
//                if(stu.getTot_cred()-credit_used>0) stu.setCredit_left(stu.getTot_cred()-credit_used);
//                else stu.setCredit_left(0);
//                studentMapper.update(stu);
//            }
//            resp.setMassage("Successfully update credit_left field of student table!");
//            resp.setStatus("Success");
//            return resp.toJSON();
//
//        }catch (Exception e){
//            resp.setFailed(e);
//            return resp.toJSON();
//        }
//    }
}
