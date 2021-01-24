package com.tatsinktech.echat.service.impl;


import com.tatsinktech.echat.dao.LoginInfoDAO;
import com.tatsinktech.echat.dao.MessageRecordDAO;
import com.tatsinktech.echat.dao.UserDAO;
import com.tatsinktech.echat.model.LoginInfoDo;
import com.tatsinktech.echat.model.MessageRecordDo;
import com.tatsinktech.echat.model.User;
import com.tatsinktech.echat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service implementation class
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginInfoDAO loginInfoDAO;
    @Autowired
    private MessageRecordDAO messageRecordDAO;


    public User validateUserPassword(String name, String password) {
        return userDAO.queryUser(name, password);
    }

    public boolean isExistUser(String name) {
        User user = userDAO.queryUserByName(name);
        return user != null;
    }

    public void insertUser(String name, String password) {
        userDAO.insertUser(name, password);
    }

    @Override
    public void addUserLoginInfo(LoginInfoDo loginInfoDo) {
        loginInfoDAO.addLoginInfo(loginInfoDo);
    }

    @Override
    public void addUserMessageRecord(MessageRecordDo messageRecordDo) {
        messageRecordDAO.addMessageRecord(messageRecordDo);
    }

    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByName(name);
    }
}
