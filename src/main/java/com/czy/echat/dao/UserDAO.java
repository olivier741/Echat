package com.czy.echat.dao;

import com.czy.echat.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * User dao interface
 */
@Mapper
public interface UserDAO {

    /**
     * Query users by username and password
     * @param name
     * @param password
     * @return returns the user object if it exists, returns null if it does not
     * exist
     */
    @Select({"SELECT id, name, password FROM user WHERE name = #{name} AND password = #{password}"})
    User queryUser(@Param("name") String name, @Param("password") String password);

    /**
     * Query users by username
     *
     * @param name
     * @return If the user name exists, return the user object, otherwise return
     * null
     */
    @Select({"SELECT id, name, password FROM user WHERE name = #{name}"})
    User queryUserByName(@Param("name") String name);

    /**
     * Insert a user
     *
     * @param name
     * @param password
     */
    @Insert({"INSERT INTO user(name, password) VALUES(#{name}, #{password})"})
    void insertUser(@Param("name") String name, @Param("password") String password);

    /**
     * Get user information based on user name
     *
     * @param name
     * @return
     */
    @Select({"SELECT * FROM user where name = #{name} limit 1"})
    User getUserByName(@Param("name") String name);

}
