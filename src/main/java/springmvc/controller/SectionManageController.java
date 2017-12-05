package springmvc.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springmvc.mapper.StudentMapper;
import springmvc.mapper.TakesMapper;
import springmvc.pojo.*;
import springmvc.service.*;
import util.Resp;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class SectionManageController {


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

    @RequestMapping("/takenSections")
    public @ResponseBody String studentTaken(HttpSession session){
        Resp resp = new Resp();
        HashMap<String, Object> result = new HashMap<>();
        try{
            Student student = studentService.getStudent(session.getAttribute("stuID").toString());
            result.put("Student", student);
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
            result.put("coursesTaken", cs);
            result.put("creditUsed", creditSelected);
            resp.setData(result);
            return JSON.toJSONString(resp);
        }catch (Exception e){
            resp.setFailed(e);
            return JSON.toJSONString(resp);
        }


    }

    @RequestMapping("querySections")
    public @ResponseBody String querySections(HttpSession session, String course_id, String title, String credits, String dept_name, String instructor_name, String time){
        Resp resp = new Resp();
        try{
            int credit;
            if(credits!=null && !credits.equals("")){
                credit = Integer.parseInt(credits);
            }else credit = -631607793;
            String instructor_id = instructorService.getInstructorID(instructor_name);
            List<Section> secs = sectionService.querySection(course_id, title,dept_name,credit,instructor_id, time);
            for(Section s: secs){
                s.setInstructor_name(instructorService.get(s.getInstructor_id()).getName());
            }
            resp.setData(secs);
            return JSON.toJSONString(resp);
        }catch(Exception e){
            resp.setFailed(e);
            return  JSON.toJSONString(e);
        }
    }

    @RequestMapping(value = "takeSection")
    public @ResponseBody String takeSection(HttpSession session, String course_id, String sec_id, String semester, String yr){
        Resp resp = new Resp();
        HashMap<String, Object> result = new HashMap<>();
        int year;
        if(yr == null || yr.equals("")) year=2018;
        else year = Integer.parseInt(yr);
        try {
            if(year!=2018){
                resp.setFailed("You can only take section in 2018!");
                return JSON.toJSONString(resp);
            }
            Section section = sectionService.getSection(course_id, sec_id, semester, year);
            if(studentService.getStudent(session.getAttribute("stuID").toString()).getCredit_left() < courseService.getCourse(course_id).getCredits()){
                resp.setFailed("Your credit is not enough!");
                return resp.toJSON();
            }
            takesService.selectSection(session.getAttribute("stuID").toString(), course_id, sec_id, semester, year);
            result.put("Student", studentService.getStudent(session.getAttribute("stuID").toString()));
            result.put("sectionSelected", section);
            result.put("isSelectSuccess", takesService.isSelected(session.getAttribute("stuID").toString(), course_id, sec_id, semester, year));
            resp.setData(result);
            return JSON.toJSONString(resp);
        }catch (Exception e){
            resp.setFailed(e);
            return JSON.toJSONString(resp);
        }
    }

    @RequestMapping("deleteTakenSection")
    public @ResponseBody String deleteTakenSection(HttpSession session, String course_id, String sec_id, String semester, String yr){
        Resp resp = new Resp();
        Takes takes = takesService.get(session.getAttribute("stuID").toString(), course_id, sec_id, semester, Integer.parseInt(yr));
        if(takes.getGrade()==null){
            try{
                takesMapper.delete(takes);
                resp.setData(takes, "Successfully remove this taken section!");
                return resp.toJSON();
            }catch(Exception e){
                resp.setFailed(e);
                return resp.toJSON();
            }
        }else{
            resp.setFailed("You can remove a section which have already finished!");
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
