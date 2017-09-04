package com.akku.service.impl;

import com.akku.pojo.User;
import com.akku.service.UserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.dao.Dao;
import org.nutz.lang.random.R;
import org.nutz.service.IdNameEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by akku on 2017/6/15.
 */
@Service
public class UserServiceImpl extends IdNameEntityService<User> implements UserService{

    @Autowired
    Dao dao;
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public User add(String name, String password) {
        if(this.dao()==null)
            this.setDao(dao);
        User user = new User();
        user.setName(name.trim());
        user.setSalt(R.UU16());
        user.setPassword(new Sha256Hash(password, user.getSalt()).toHex());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return dao().insert(user);
    }

    @Override
    public Integer fetch(String username, String password) {
        if(this.dao()==null)
            this.setDao(dao);
        User user = fetch(username);
        if (user == null) {
            return -1;
        }
        String _pass = new Sha256Hash(password, user.getSalt()).toHex();
        if(_pass.equalsIgnoreCase(user.getPassword())) {
            return user.getId();
        }
        return -1;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer updatePassword(int userId, String password) {
        if(this.dao()==null)
            this.setDao(dao);
        User user = fetch(userId);
        if (user == null) {
            return -1;
        }
        user.setSalt(R.UU16());
        user.setPassword(new Sha256Hash(password, user.getSalt()).toHex());
        user.setUpdateTime(new Date());
        return dao().update(user, "^(password|salt|updateTime)$");
    }
}
