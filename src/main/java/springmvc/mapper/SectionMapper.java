package springmvc.mapper;

import org.apache.ibatis.annotations.Param;
import springmvc.pojo.Section;

public interface SectionMapper {
    public Section getSection(@Param("course_id") String course_id, @Param("sec_id") String sec_id, @Param("semester") String semester, @Param("year") int year);
}
