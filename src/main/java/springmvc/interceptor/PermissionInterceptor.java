package springmvc.interceptor;

import annotations.Permission_All;
import annotations.Permission_Instructor;
import annotations.Permission_Root;
import annotations.Permission_Student;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import springmvc.mapper.UserMapper;
import springmvc.pojo.User;
import util.Resp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

public class PermissionInterceptor implements HandlerInterceptor{
    @Autowired
    UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        try{
            Method method = ((HandlerMethod) handler).getMethod();
            if(method.getAnnotation(Permission_All.class) != null) return true;
            try{
                User user = userMapper.get(session.getAttribute("userID").toString());
                boolean isPass = false;
                if(method.getAnnotation(Permission_Instructor.class) != null) isPass = user.getUser_group().equals("Instructor");
                if(method.getAnnotation(Permission_Student.class) != null) {
                    if(!isPass) isPass = user.getUser_group().equals("Student");
                }
                if(method.getAnnotation(Permission_Root.class) != null){
                    if(!isPass) isPass = user.getUser_group().equals("Root");
                }
                if(!isPass){
                    Resp resp = new Resp();
                    resp.setFailed("UNAUTHORIZED");
                    httpServletResponse.getWriter().write(resp.toJSON());
                }
                return isPass;
            }catch (Exception e){
                Resp resp = new Resp();
                resp.setFailed(e);
                httpServletResponse.getWriter().write(resp.toJSON());
                return false;
            }
        }catch (ClassCastException e){
            httpServletResponse.setStatus(404);
            httpServletResponse.getWriter().write("");
            return true;
        }




    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }


}
