package springmvc.controller;


import annotations.*;
import cn.dsna.util.images.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springmvc.mapper.CourseMapper;
import springmvc.mapper.UserMapper;
import springmvc.pojo.*;
import springmvc.service.*;
import util.MD5;
import util.Resp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    UserMapper userMapper;

    @CrossOrigin
    @Permission_All
    @RequestMapping("/getVerificationCode")
    public @ResponseBody String login(HttpServletRequest request, HttpSession session) throws IOException {
        Resp resp = new Resp();
        try{
            ValidateCode codeGen = new ValidateCode(120,40,4,100);
            String filename = UUID.randomUUID().toString().replace("-", "");
            codeGen.write(request.getServletContext().getRealPath("/static/image/")+filename+".png");
            HashMap<String, String> result = new HashMap<>();
            result.put("url", "/static/image/"+filename+".png");
            session.setAttribute("captcha", codeGen.getCode());
//            result.put("verifyARGS", MD5.toMD5(codeGen.getCode()+"Prophet"));
            resp.setData(result);
//            response.setHeader("Access-Control-Allow-Origin", "*");
            return resp.toJSON();
        }catch (Exception e){
            resp.setFailed(e,"Error generating Verification Code, Please try again!");
            return resp.toJSON();
        }

     }

    @CrossOrigin
    @Permission_All
    @RequestMapping(value = "/verifyLogin", method = RequestMethod.POST)
    public @ResponseBody String login(String id, String pwd, String user_group, String captcha, HttpSession session, HttpServletResponse response){
        Resp resp = new Resp();
        if(id == null || pwd == null || id.equals("") || pwd.equals("")){
            resp.setFailed("ID or password can not be null!");
            return resp.toJSON();
        }
        if(captcha == null || captcha.equals("")){
            resp.setFailed("Please input the captcha code!");
            return resp.toJSON();
        }
        if(!captcha.equals(session.getAttribute("captcha"))){
            resp.setFailed("Wrong captcha code! Please try again!");
            return resp.toJSON();
        }
        if(user_group == null || user_group.equals("")){
            resp.setFailed("You must select your user group!");
            return resp.toJSON();
        }
        try{
            User user = userMapper.getUser(id, user_group);
            if( user != null && user.getPassword().equals(MD5.toMD5(pwd))){
                session.setAttribute("userID", user.getId());
//                session.setAttribute("user_group", user.getUser_group());
//                Cookie cookie_username = new Cookie("username", user.getUsername());
//                response.addCookie(cookie_username);
                resp.setData("Login Success!");
                return resp.toJSON();
            }else{
                resp.setFailed("Wrong ID or password! Please try again!");
                return resp.toJSON();
            }
        }catch(Exception e){
            resp.setFailed(e, "Unknown error!");
            return resp.toJSON();
        }

    }

    @CrossOrigin
    @Permission_Student
    @Permission_Instructor
    @Permission_Root
    @RequestMapping(value = "/getUser")
    public @ResponseBody String getUser(HttpSession session){
        Resp resp = new Resp();
        try{
            User user = userMapper.get(session.getAttribute("userID").toString());
            user.setPassword("");
            resp.setData(user);
            return resp.toJSON();
        }catch (Exception e){
            resp.setFailed("Please login first!");
            return resp.toJSON();
        }
    }

    @CrossOrigin
    @Permission_Student
    @Permission_Root
    @Permission_Instructor
    @RequestMapping("/logout")
    public @ResponseBody String logOut(HttpSession session){
        Resp resp = new Resp();
        try{
            session.invalidate();
            resp.setData("Log out Success!");
            return resp.toJSON();
        }catch (Exception e){
            resp.setFailed("Unknown Error! Please try again!");
            return resp.toJSON();
        }
    }

    @CrossOrigin
    @Permission_Student
    @Permission_Root
    @Permission_Instructor
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public @ResponseBody String changePassword(HttpSession session, String previousPwd, String newPwd){
        Resp resp = new Resp();
        try{
            User user = userMapper.get(session.getAttribute("userID").toString());
            if(user.getPassword().equals(MD5.toMD5(previousPwd))){
                if(newPwd.equals(previousPwd)){
                    resp.setFailed("The new password shouldn't be the same as previous!");
                    return resp.toJSON();
                }
                user.setPassword(MD5.toMD5(newPwd));
                userMapper.update(user);
                resp.setStatus("success");
                resp.setMessage("Successfully changed your password! Please login again!");
                session.invalidate();
                return resp.toJSON();
            }else{
                resp.setFailed("The Previous password is not correct, please try again!");
                return resp.toJSON();
            }
        }catch (Exception e){
            resp.setFailed("Unknown Error! Please try again!");
            return resp.toJSON();
        }
    }

    @CrossOrigin
    @Permission_Student
    @Permission_Root
    @Permission_Instructor
    @RequestMapping("getProfile")
    public @ResponseBody String getProfile(HttpSession session){
        Resp resp = new Resp();
        try{
            User user = userMapper.get(session.getAttribute("userID").toString());
            if(user.getUser_group().equals("Student")){
                resp.setData(userMapper.getStudentProfile(user.getGroup_id()));
                return resp.toJSON();
            }
            return "";
            //To be Continue...
        }catch (Exception e){
            resp.setFailed("Unknown Error! Please try again!");
            return resp.toJSON();
        }
    }

    @CrossOrigin
    @Permission_Student
    @Permission_Root
    @Permission_Instructor
    @RequestMapping(value = "updateProfile", method = RequestMethod.POST)
    public @ResponseBody String getProfile(HttpSession session, String username, String email, String phone){
        Resp resp = new Resp();
        try{
            User user = userMapper.get(session.getAttribute("userID").toString());
            if(user.getUser_group().equals("Student")){
                if(!username.equals("")) user.setUsername(username);
                if(!email.equals("")) user.setEmail(email);
                if(!phone.equals("")) user.setPhone(phone);
                userMapper.update(user);
                resp.setStatus("success");
                return resp.toJSON();
            }
            return "";
            //To be Continue...
        }catch (Exception e){
            resp.setFailed("Unknown Error! Please try again!");
            return resp.toJSON();
        }
    }
}
