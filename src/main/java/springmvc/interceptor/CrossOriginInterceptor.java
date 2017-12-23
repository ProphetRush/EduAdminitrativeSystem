package springmvc.interceptor;

import annotations.CrossOrigin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class CrossOriginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        try{
            Method method = ((HandlerMethod) handler).getMethod();
            if(method.getAnnotation(CrossOrigin.class)!=null) {
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8080");
                httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

            }
        }catch (ClassCastException e){
            return true;
        }
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
}
