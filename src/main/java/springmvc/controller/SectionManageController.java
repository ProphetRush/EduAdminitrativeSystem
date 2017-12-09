package springmvc.controller;


import annotations.Permission_Instructor;
import annotations.Permission_Student;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springmvc.mapper.CourseMapper;
import springmvc.mapper.StudentMapper;
import springmvc.mapper.TakesMapper;
import springmvc.mapper.UserMapper;
import springmvc.pojo.*;
import springmvc.service.*;
import util.Resp;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    UserMapper userMapper;

    @Permission_Student
    @RequestMapping("/takenSections")
    public @ResponseBody String studentTaken(HttpSession session){
        Resp resp = new Resp();
        HashMap<String, Object> result = new HashMap<>();
        try{
            String stuID = userMapper.get(session.getAttribute("userID").toString()).getGroup_id();
            Student student = studentService.getStudent(stuID);
            result.put("Student", student);
            List<Takes> takes = takesService.studentTakes(stuID);
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


    @Permission_Student
    @Permission_Instructor
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

    @Permission_Student
    @RequestMapping(value = "takeSection", method = RequestMethod.POST)
    public @ResponseBody String takeSection(HttpSession session, String course_id, String sec_id, String semester, String yr){
        Resp resp = new Resp();
        HashMap<String, Object> result = new HashMap<>();
        int year;
        String stuID = userMapper.get(session.getAttribute("userID").toString()).getGroup_id();
        if(yr == null || yr.equals("")) year=2018;
        else year = Integer.parseInt(yr);
        try {
            if(year!=2018){
                resp.setFailed("You can only take section in 2018!");
                return JSON.toJSONString(resp);
            }
            String PrereqID = courseMapper.getPrereqID(course_id);
            Section section = sectionService.getSection(course_id, sec_id, semester, year);
            if(PrereqID == null||takesMapper.getSelectedCourseID(stuID).contains(PrereqID)){
                if(studentService.getStudent(stuID).getCredit_left() < courseService.getCourse(course_id).getCredits()){
                    resp.setFailed("Your credit is not enough!");
                    return resp.toJSON();
                }
                if(!takesService.isCapacityEnough(section)){
                    resp.setFailed("This section is full, please select another section!");
                    return resp.toJSON();
                }
                takesService.selectSection(stuID, course_id, sec_id, semester, year);
                result.put("Student", studentService.getStudent(stuID));
                result.put("sectionSelected", section);
                result.put("isSelectSuccess", takesService.isSelected(stuID, course_id, sec_id, semester, year));
                resp.setData(result);
                return JSON.toJSONString(resp);
            }else {
                resp.setFailed("You have not take the pre-required course: "+ courseService.getCourse(PrereqID).getTitle() + ", Please take this course first!");
                return JSON.toJSONString(resp);
            }

        }catch (Exception e){
            resp.setFailed(e);
            return JSON.toJSONString(resp);
        }
    }

    @Permission_Student
    @RequestMapping(value = "deleteTakenSection", method = RequestMethod.POST)
    public @ResponseBody String deleteTakenSection(HttpSession session, String course_id, String sec_id, String semester, String yr){
        Resp resp = new Resp();
        try{
            String stuID = userMapper.get(session.getAttribute("userID").toString()).getGroup_id();
            Takes takes = takesService.get(stuID, course_id, sec_id, semester, Integer.parseInt(yr));
            if(takes.getGrade()==null){
                takesMapper.delete(takes);
                resp.setData(takes, "Successfully remove this taken section!");
                return resp.toJSON();
            }else{
                resp.setFailed("You can't remove a section which have already finished!");
                return resp.toJSON();
            }
        }catch(Exception e){
            resp.setFailed(e);
            return resp.toJSON();
        }


    }

    @Permission_Student
    @RequestMapping("getGrades")
    public @ResponseBody String getStuGrades(HttpSession session){
        Resp resp = new Resp();
        String stuID = userMapper.get(session.getAttribute("userID").toString()).getGroup_id();
        try{
            List<HashMap<String, String>> grades = takesMapper.getStuGrades(stuID);
            resp.setData(grades);
            return resp.toJSON();
        }catch(Exception e){
            resp.setFailed(e);
            return resp.toJSON();
        }
    }

    @Permission_Student
    @RequestMapping("getGradesByTerms")
    public @ResponseBody String getGradesByTerms(HttpSession session, String year, String semester){
        Resp resp = new Resp();
        String stuID = userMapper.get(session.getAttribute("userID").toString()).getGroup_id();
        try{
            List<HashMap<String, String>> grades = takesMapper.getStuGradesByTerms(stuID, year, semester);
            resp.setData(grades);
            return resp.toJSON();
        }catch(Exception e){
            resp.setFailed(e);
            return resp.toJSON();
        }
    }



}
