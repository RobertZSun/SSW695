package com.hobbymatcher.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hobbymatcher.dao.UserDao;
import com.hobbymatcher.entity.User;
import com.hobbymatcher.service.UserService;

import javax.validation.Valid;

@Valid
@Service
public class UserServiceImpl implements UserService {

    Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public Boolean register(User user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            userDao.insertUser(user);
            return true;
        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        }
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public Boolean login(String email, String password) {
        return userDao.findUserByEmailAndPwd(email, encoder.encode(password)) != null;
    }

    @Override
    public Boolean updateUser(User user) {
        try {
            if (user.getPassword() != null) {
                user.setPassword(encoder.encode(user.getPassword()));
            }

            return userDao.updateUser(user) == 1;
        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteUser(Integer id) {
        try {
            userDao.deleteUser(id);
            return true;
        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails details = findUserByEmail(username);
        if (details == null) {
            System.err.println(username);
            throw new UsernameNotFoundException("");
        }
        return details;
    }

    @Override
    public Boolean updateEmail(Integer id, String newEmail) {
        userDao.updateEmail(id, newEmail);
        return true;
    }

    @Override
    public Boolean updatePassword(Integer id, String newPassword) {
        userDao.updatePassword(id, encoder.encode(newPassword));
        return true;
    }
}
