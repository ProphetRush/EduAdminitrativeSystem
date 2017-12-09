package springmvc.mapper;


import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Section;
import springmvc.pojo.Student;
import springmvc.pojo.Takes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TakesMapper {
    public void add(Takes takes);
    public void delete(Takes takes);
    public List<Takes> studentTakes(String stuID);
    public int count();
    public void selectSection(@Param("sid") String stuID, @Param("cid") String course_id, @Param("sec_id") String sec_id, @Param("semester") String semester, @Param("year") int year);
    public Takes get(@Param("sid") String stuID, @Param("cid") String course_id, @Param("sec_id") String sec_id, @Param("semester") String semester, @Param("year") int year);
    public List<String> getSelectedCourseID(String stuID);
    public int getSelectedCount(Section section);
    public int getCapacity(@Param("building") String building, @Param("room_number") String room_number);

    public List<HashMap<String, String>> getStuGrades(String stuID);
    public List<HashMap<String, String>> getStuGradesByTerms(@Param("stuID") String stuID, @Param("year")String year, @Param("semester")String semester);
}
