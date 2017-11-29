package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.SectionMapper;
import springmvc.pojo.Section;
import springmvc.service.SectionService;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    SectionMapper sectionMapper;

    @Override
    public Section getSection(String course_id, String sec_id, String semester, int year) {
        return sectionMapper.getSection(course_id, sec_id, semester, year);
    }
}
