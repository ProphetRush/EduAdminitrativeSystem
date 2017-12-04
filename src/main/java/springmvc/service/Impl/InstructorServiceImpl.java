package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.InstructorMapper;
import springmvc.pojo.Instructor;
import springmvc.service.InstructorService;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService{
    @Autowired
    InstructorMapper instructorMapper;

    @Override
    public Instructor get(String ID) {
        return instructorMapper.get(ID);
    }

    @Override
    public String getInstructorID(String instructor_name) {
        return instructorMapper.getInstructorID(instructor_name);
    }

    @Override
    public List<String> getAllInstructorIDByDept(String dept_name) {
        return instructorMapper.getAllInstructorIDByDept(dept_name);
    }
}
