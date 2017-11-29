package springmvc.service;

import springmvc.pojo.Section;

public interface SectionService {
    Section getSection(String course_id, String sec_id, String semester, int year);
}
