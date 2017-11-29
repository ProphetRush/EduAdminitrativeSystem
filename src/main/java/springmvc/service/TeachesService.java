package springmvc.service;

import springmvc.pojo.Teaches;

public interface TeachesService {
    String getTeacherID(String course_id, String sec_id, String semester, int year);
}
