package springmvc.mapper;

import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Section;

import java.util.HashMap;
import java.util.List;

public interface SectionMapper {
    public Section getSection(@Param("course_id") String course_id, @Param("sec_id") String sec_id, @Param("semester") String semester, @Param("year") int year);
    public List<HashMap<String, String>> querySection(@Param("course_id") String course_id, @Param("title") String course_name,
                                                      @Param("dept_name") String dept_name, @Param("credits") String credits,
                                                      @Param("instructor_name") String instructor_name, @Param("time") String time,
                                                      @Param("semester") String semester, @Param("year") String year,@Param("prereq") String prereq);
    public List<String> getAllRoomNumbers();
    public List<String> getRoomNumberByBuilding(String building);
    public int getSecIDByCourse(String course_id);
    public void AddSection(Section section);

}
