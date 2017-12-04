package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.CourseMapper;
import springmvc.mapper.InstructorMapper;
import springmvc.mapper.SectionMapper;
import springmvc.pojo.Course;
import springmvc.pojo.Section;
import springmvc.service.CourseService;
import springmvc.service.InstructorService;
import springmvc.service.SectionService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    SectionMapper sectionMapper;
    @Autowired
    InstructorMapper instructorMapper;


    public List<Course> list(){
        return courseMapper.list();
    };

    public Course getCourse(String id){
        return courseMapper.get(id);
    }

    public List<Course> queryCourse(String course_id, String title, String dept_name, int credits) {
        return courseMapper.query(course_id, title, dept_name, credits);
    }

    @Override
    public List<String> getAllCoursesID() {
        return courseMapper.getAllCoursesID();
    }

    @Override
    public void AutoAddCourse(int count) {
        String[] coursesID = courseMapper.getAllCoursesID().toArray(new String[200]);
        String[] semesters = {"Spring", "Fall"};
        int year = 2018;
        String[] buildings = {"Alumni", "Bronfman", "Chandler", "Drown", "Fairchild", "Garfield", "Gates", "Grace", "Lambeau", "Lamberton", "Main",
        "Nassau", "Painter", "Polya", "Power", "Rathbone", "Saucon", "Stabler", "Taylor", "Whitman"};
        String[] time_slot_ids = {"A", "B", "C", "D", "E", "F", "G", "H"};
//        String[] room_numbers = sectionMapper.getAllRoomNumbers().toArray(new String[30]);
//        String[] instructor_ids = instructorMapper.getAllInstructorID().toArray(new String[50]);
        for (int i = 0; i < count; i++) {
            String cid = coursesID[(int)(Math.random()*coursesID.length)];
            List<String> ins = instructorMapper.getAllInstructorIDByDept(courseMapper.getDeptNameByCourseID(cid));
            String[] instructor_ids = ins.toArray(new String[ins.size()]);
            String building = buildings[(int)(Math.random()*buildings.length)];
            List<String> rn = sectionMapper.getRoomNumberByBuilding(building);
            String[] room_number = rn.toArray(new String[rn.size()]);
            courseMapper.AddCourse(cid, (sectionMapper.getSecIDByCourse(cid)+1)+"", semesters[(int)(Math.random()*2)],
                    year, building, room_number[(int)(Math.random()*room_number.length)], time_slot_ids[(int)(Math.random()*time_slot_ids.length)],
                    instructor_ids[(int)(Math.random()*instructor_ids.length)]);
        }
    }
}
