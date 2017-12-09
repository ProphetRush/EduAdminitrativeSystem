package springmvc.mapper;

import org.apache.ibatis.annotations.Param;
import springmvc.pojo.User;

import java.util.List;

public interface UserMapper {
    public void add(String ID, String group_id, String user_group, String password);
    public void initUsers(List<User> users);
    public User getUser(@Param("gid") String id, @Param("ug") String user_group);
    public User get(String id);
}
