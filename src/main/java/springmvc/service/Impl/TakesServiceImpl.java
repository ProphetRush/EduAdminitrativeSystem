package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.TakesMapper;
import springmvc.pojo.Takes;
import springmvc.service.TakesService;

import java.util.List;

@Service
public class TakesServiceImpl implements TakesService{

    @Autowired
    TakesMapper takesMapper;

    @Override
    public List<Takes> studentTakes(String stuID) {
        return takesMapper.studentTakes(stuID);
    }

    @Override
    public void selectSection(String stuID, String course_id, String sec_id, String semester, int year) {
        takesMapper.selectSection(stuID, course_id, sec_id, semester, year);
    }

    @Override
    public Boolean isSelected(String stuID, String course_id, String sec_id, String semester, int year) {
        return takesMapper.get(stuID, course_id, sec_id, semester, year) != null;
    }

    @Override
    public Takes get(String stuID, String course_id, String sec_id, String semester, int year) {
        return takesMapper.get(stuID, course_id, sec_id, semester, year);
    }
}
