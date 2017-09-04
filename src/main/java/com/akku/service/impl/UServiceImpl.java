package com.akku.service.impl;

import com.akku.pojo.UInfo;
import com.akku.pojo.UPay;
import com.akku.service.UService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by akku on 2017/6/14.
 */
@Service
public class UServiceImpl implements UService {
    @Autowired
    Dao dao;
    @Override
    @Cacheable(value = "list_UInfo")
    public List<UInfo> list() {
        return dao.query(UInfo.class,null);
    }

    @Override
    @Cacheable(value = "get_UInfo",key = "'get_UInfo'+#id")
    public UInfo get(int id) {
        return dao.fetch(UInfo.class,id);
    }

    @Override
    @CacheEvict(value = {"list_UInfo","get_UInfo"},key = "'get_UInfo'+#id",allEntries = true)
    public Integer delete(Integer id) {
        return dao.delete(UInfo.class,id);
    }

    @Override
    @CacheEvict(value = {"list_UInfo","get_UInfo"},key = "'get_UInfo'+#id",allEntries = true)
    public UInfo insert(UInfo u) {
        return dao.insert(u);
    }

    @Override
    @CacheEvict(value = {"list_UInfo","get_UInfo"},key = "'get_UInfo'+#u.id",allEntries = true)
    public Integer update(UInfo u) {
        return dao.update(u);
    }

    @Override
    @Cacheable(value = "get_PayList",key = "'get_PayList'+#uId")
    public List<UPay> getPayList(Integer uId) {
        return dao.query(UPay.class, Cnd.where("uid","=",uId));
    }

    @Override
    @CacheEvict(value = {"list_UInfo","get_UInfo","get_PayList"},key = "('get_UInfo'+#uId)or ('get_PayList'+#uId)",allEntries = true)
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public UInfo pay(Integer uId, Double money) throws Exception{

            UPay pay = new UPay();
            pay.setuId(uId);
            pay.setMoney(money);
            pay.setTime(new Date());
            dao.insert(pay);
            UInfo info = dao.fetch(UInfo.class,uId);
            info.setMoney(info.getMoney()-money);
            dao.update(info);
            return dao.fetch(UInfo.class,uId);

    }

}
