package com.akku.service;

import com.akku.pojo.UInfo;
import com.akku.pojo.UPay;

import java.util.List;

/**
 * Created by akku on 2017/6/14.
 */
public interface UService {
    List<UInfo> list();
    UInfo get(int id);
    Integer delete(Integer id);
    UInfo insert(UInfo u);
    Integer update(UInfo u);

    List<UPay> getPayList(Integer uId);
    UInfo pay(Integer uId,Double money) throws Exception;

}
