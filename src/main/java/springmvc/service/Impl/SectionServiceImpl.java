package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.CourseMapper;
import springmvc.mapper.InstructorMapper;
import springmvc.mapper.SectionMapper;
import springmvc.pojo.Course;
import springmvc.pojo.Section;
import springmvc.service.SectionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    SectionMapper sectionMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    InstructorMapper instructorMapper;

    @Override
    public Section getSection(String course_id, String sec_id, String semester, int year) {
        return sectionMapper.getSection(course_id, sec_id, semester, year);
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

    @Override
    public List<Section> AutoAddSections(int count) {
        String[] coursesID = courseMapper.getAllCoursesID().toArray(new String[200]);
        String[] semesters = {"Spring", "Fall"};
        int year = 2018;
        String[] buildings = {"Alumni", "Bronfman", "Chandler", "Drown", "Fairchild", "Garfield", "Gates", "Grace", "Lambeau", "Lamberton", "Main",
                "Nassau", "Painter", "Polya", "Power", "Rathbone", "Saucon", "Stabler", "Taylor", "Whitman"};
        String[] time_slot_ids = {"A", "B", "C", "D", "E", "F", "G", "H"};
//        String[] room_numbers = sectionMapper.getAllRoomNumbers().toArray(new String[30]);
//        String[] instructor_ids = instructorMapper.getAllInstructorID().toArray(new String[50]);
        List<Section> sectionsAdded = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String cid = coursesID[(int)(Math.random()*coursesID.length)];
            List<String> ins = instructorMapper.getAllInstructorIDByDept(courseMapper.getDeptNameByCourseID(cid));
            String[] instructor_ids = ins.toArray(new String[ins.size()]);
            String building = buildings[(int)(Math.random()*buildings.length)];
            List<String> rn = sectionMapper.getRoomNumberByBuilding(building);
            String[] room_number = rn.toArray(new String[rn.size()]);
            Section section = new Section(cid, (sectionMapper.getSecIDByCourse(cid)+1)+"", semesters[(int)(Math.random()*2)],
                    year, building, room_number[(int)(Math.random()*room_number.length)], time_slot_ids[(int)(Math.random()*time_slot_ids.length)],
                    instructor_ids[(int)(Math.random()*instructor_ids.length)]);
            sectionMapper.AddSection(section);
            sectionsAdded.add(section);
        }
        return sectionsAdded;
    }
}
