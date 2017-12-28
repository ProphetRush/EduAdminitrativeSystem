package springmvc.service;

import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Section;

import java.util.List;

public interface SectionService {
    Section getSection(String course_id, String sec_id, String semester, int year);
    List<String> getAllRoomNumbers();
    List<String> getRoomNumberByBuilding(String building);
    int getSecIDByCourse(String course_id);
    List<Section> AutoAddSections(int count);
}
