package springmvc.service;

import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Section;

import java.util.List;

public interface SectionService {
    Section getSection(String course_id, String sec_id, String semester, int year);
    List<Section> querySection(String course_id, String course_name, String dept_name, int credits, String instructor_id, String time);
    List<String> getAllRoomNumbers();
    List<String> getRoomNumberByBuilding(String building);
    int getSecIDByCourse(String course_id);
}
