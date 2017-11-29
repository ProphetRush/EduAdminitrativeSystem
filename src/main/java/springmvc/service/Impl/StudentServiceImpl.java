package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.StudentMapper;
import springmvc.pojo.Student;
import springmvc.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentMapper studentMapper;

    @Override
    public Student getStudent(String ID) {
        return studentMapper.get(ID);
    }
}
