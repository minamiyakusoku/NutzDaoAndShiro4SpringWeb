package com.akku.service;

import com.akku.pojo.User;

/**
 * Created by akku on 2017/6/15.
 */
public interface UserService {
    User add(String name, String password);
    Integer fetch(String username, String password);
    Integer updatePassword(int userId, String password);
}
