package springmvc.mapper;


import springmvc.pojo.Student;
import springmvc.pojo.Takes;

import java.util.List;

public interface TakesMapper {
    public void add(Takes takes);
    public void delete(Takes takes);
    public List<Takes> studentTakes(String stuID);
    public int count();
}
