package com.akku.controller;

import com.akku.pojo.UInfo;
import com.akku.pojo.UPay;
import com.akku.service.UService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by akku on 2017/6/14.
 */
@Controller
@Api
@RequestMapping("/u_test")
public class UController {
    @Autowired
    UService ser;
    @RequestMapping(value ="list_info",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<UInfo> list(){
        List<UInfo> list = ser.list();

        return list;
    }

    @RequestMapping(value ="get_info",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public UInfo get(@RequestParam("id")Integer id){
        return ser.get(id);
    }


    @RequestMapping(value ="delete_info",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<UInfo> delete(@RequestParam("id")Integer id){
        ser.delete(id);
        List<UInfo> list = ser.list();
        return list;
    }


    @RequestMapping(value ="insert_info",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public UInfo insert(@RequestParam("u")UInfo uInfo){
        return ser.insert(uInfo);
    }


    @RequestMapping(value ="update_info",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public UInfo update(@RequestParam("u")UInfo uInfo){
        ser.update(uInfo);
        return ser.get(uInfo.getId());
    }

    @RequestMapping(value ="get_pay_list",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<UPay> getPayList(@RequestParam("uid")Integer uId){
        return ser.getPayList(uId);
    }

    @RequestMapping(value ="pay",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public UInfo pay(@RequestParam("uid")Integer uId,@RequestParam("money") Double money){
        try {
            return ser.pay(uId,money);
        } catch (Exception e) {
            return null;
        }
    }
}
