package com.akku.job.nutz.init;

import com.akku.pojo.User;
import com.akku.service.UserService;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by akku on 2017/6/15.
 */
public class NutzInit implements ApplicationListener<ContextRefreshedEvent>

{
    @Autowired
    Dao dao;
    @Autowired
    UserService ser;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
             // 如果没有createTablesInPackage,请检查nutz版本
        Daos.createTablesInPackage(dao, "com.akku", false);
        if (dao.count(User.class) == 0) {
            ser.add("admin", "123456");
        }
    }
}
