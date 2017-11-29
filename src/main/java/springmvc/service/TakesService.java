package springmvc.service;

import springmvc.pojo.Takes;

import java.util.List;

public interface TakesService {
    List<Takes> studentTakes(String stuID);
}
