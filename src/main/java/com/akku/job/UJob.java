package com.akku.job;

import com.akku.pojo.UInfo;
import com.akku.service.UService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by akku on 2017/6/14.
 */
@Component
public class UJob {
    @Autowired
    UService ser;
//    @Scheduled(cron="0/5 * *  * * ? ")
    public void buy() {
        Double money = new Double((int)(Math.random()*100));
        try {
            ser.pay(1,money);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    @Scheduled(cron="0/10 * *  * * ? ")
    public void recharge(){

        Double money = new Double((int)(Math.random()*200));
        try {
            UInfo u = ser.get(1);
            u.setMoney(u.getMoney()+money);
            ser.update(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
