package springmvc.controller;


import annotations.Permission_All;
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

import javax.servlet.http.HttpServletRequest;
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


    @Permission_All
    @RequestMapping("/getVerificationCode")
    public @ResponseBody String login(HttpSession session, HttpServletRequest request) throws IOException {
        Resp resp = new Resp();
        try{
            ValidateCode codeGen = new ValidateCode(160,40,4,100);
            session.setAttribute("captcha", codeGen.getCode());
            String filename = UUID.randomUUID().toString().replace("-", "");
            codeGen.write(request.getServletContext().getRealPath("/static/image/")+filename+".png");
            HashMap<String, String> result = new HashMap<>();
            result.put("url", "/static/image/"+filename+".png");
            resp.setData(result);
            return resp.toJSON();
        }catch (Exception e){
            resp.setFailed(e,"Error generating Verification Code, Please try again!");
            return resp.toJSON();
        }

     }


    @Permission_All
    @RequestMapping(value = "/verifyLogin")
    public @ResponseBody String login(String id, String pwd, String user_group, String captcha, HttpSession session){
        Resp resp = new Resp();
        if(id == null || pwd == null || id.equals("") || pwd.equals("")){
            resp.setFailed("ID or password can not be null!");
            return resp.toJSON();
        }
        if(captcha == null || captcha.equals("")){
            resp.setFailed("Please input the captcha code!");
            return resp.toJSON();
        }
        if(!captcha.equals(session.getAttribute("captcha").toString())){
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
                session.setAttribute("user_group", user.getUser_group());
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
}
