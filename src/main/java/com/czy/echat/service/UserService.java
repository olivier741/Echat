package com.czy.echat.service;

import com.czy.echat.model.LoginInfoDo;
import com.czy.echat.model.MessageRecordDo;
import com.czy.echat.model.User;

/**
 * User service interface
 */
public interface UserService {

    /**
     * Verify user password
     *
     * @param name
     * @param password
     * @return returns the user object correctly, otherwise it returns empty
     */
    public User validateUserPassword(String name, String password);

    /**
     * Is the user already registered
     *
     * @param name
     * @return
     */
    public boolean isExistUser(String name);

    /**
     * Insert a user
     *
     * @param name
     * @param password
     */
    public void insertUser(String name, String password);

    public void addUserLoginInfo(LoginInfoDo loginInfoDo);

    public void addUserMessageRecord(MessageRecordDo messageRecordDo);

    public User getUserByName(String name);
}
