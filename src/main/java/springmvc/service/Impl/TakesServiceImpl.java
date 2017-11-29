package springmvc.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.mapper.TakesMapper;
import springmvc.pojo.Takes;
import springmvc.service.TakesService;

import java.util.List;

@Service
public class TakesServiceImpl implements TakesService{

    @Autowired
    TakesMapper takesMapper;

    @Override
    public List<Takes> studentTakes(String stuID) {
        return takesMapper.studentTakes(stuID);
    }
}
