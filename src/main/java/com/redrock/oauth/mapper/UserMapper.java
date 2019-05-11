package com.redrock.oauth.mapper;


import com.redrock.oauth.entry.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Insert(" INSERT INTO user SET user_id = #{user_id}")
    int register(User user);

    @Select("Select * from user where user_name = #{user_name}")
    User getPassword(String username);

    @Select("Select * from user where user_name = #{user_id}")
    User getUser(int user_id);

    @Select("Select user_name from user where user_id = #{0}")
    String getUsername(int user_id);

    @Update("update user set user_name=#{0},password=#{1} where user_name=#{2}")
    void modifyUserInfo(String newName, String newPassword, String username);

    @Select("SELECT COUNT(user_id) FROM user WHERE user_id =#{0}}")
    int IfUserExist(int user_id);

}
