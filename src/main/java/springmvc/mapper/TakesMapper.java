package springmvc.mapper;


import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Student;
import springmvc.pojo.Takes;

import java.util.List;

public interface TakesMapper {
    public void add(Takes takes);
    public void delete(Takes takes);
    public List<Takes> studentTakes(String stuID);
    public int count();
    public void selectSection(@Param("sid") String stuID, @Param("cid") String course_id, @Param("sec_id") String sec_id, @Param("semester") String semester, @Param("year") int year);
    public Takes get(@Param("sid") String stuID, @Param("cid") String course_id, @Param("sec_id") String sec_id, @Param("semester") String semester, @Param("year") int year);
}
