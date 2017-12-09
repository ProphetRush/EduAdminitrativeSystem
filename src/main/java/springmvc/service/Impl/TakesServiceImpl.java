package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.CourseMapper;
import springmvc.mapper.TakesMapper;
import springmvc.pojo.Section;
import springmvc.pojo.Takes;
import springmvc.service.TakesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TakesServiceImpl implements TakesService{

    @Autowired
    TakesMapper takesMapper;
    @Autowired
    CourseMapper courseMapper;

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

//    @Override
//    public String[] getSelectedCourseID(String stuID) {
//        List<String> result = takesMapper.getSelectedCourseID(stuID);
//        return result.toArray(new String[result.size()]);
//    }


    @Override
    public Boolean isCapacityEnough(Section section) {
        return takesMapper.getCapacity(section.getBuilding(), section.getRoom_number())>takesMapper.getSelectedCount(section);
    }

    @Override
    public HashMap<String, String> getStuGrades(String stuID) {
//        HashMap<String, String> tmp = takesMapper.getStuGrades(stuID);
//        HashMap<String, String> grades = new HashMap<>();
//        for (Map.Entry<String, String> entry : tmp.entrySet()){
//            if(entry.getValue() != null){
//                grades.put(courseMapper.get(entry.getKey()).getTitle(), entry.getValue());
//            }
//        }
//        return grades;
        return null;
    }
}
