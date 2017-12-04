package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.SectionMapper;
import springmvc.pojo.Section;
import springmvc.service.SectionService;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    SectionMapper sectionMapper;

    @Override
    public Section getSection(String course_id, String sec_id, String semester, int year) {
        return sectionMapper.getSection(course_id, sec_id, semester, year);
    }

    @Override
    public List<Section> querySection(String course_id, String course_name, String dept_name, int credits, String instructor_id, String time) {
        return sectionMapper.querySection(course_id, course_name, dept_name, credits, instructor_id, time);
    }

    @Override
    public List<String> getAllRoomNumbers() {
        return sectionMapper.getAllRoomNumbers();
    }

    @Override
    public List<String> getRoomNumberByBuilding(String building) {
        return sectionMapper.getRoomNumberByBuilding(building);
    }

    @Override
    public int getSecIDByCourse(String course_id) {
        return sectionMapper.getSecIDByCourse(course_id);
    }
}
