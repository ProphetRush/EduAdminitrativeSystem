package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.TeachesMapper;
import springmvc.pojo.Teaches;
import springmvc.service.TeachesService;

@Service
public class TeachesServiceImpl implements TeachesService{
    @Autowired
    TeachesMapper teachesMapper;

    @Override
    public String getTeacherID(String course_id, String sec_id, String semester, int year) {
        return teachesMapper.getTeacherID(course_id, sec_id, semester,year);
    }
}
