package com.akku.pojo;

import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by akku on 2017/6/14.
 */
@Table("info")
public class UInfo implements Serializable{
    @Id
    @Column("id")
    private Integer id;
    @Column("name")
    private String name;
    @Column("money")
    private Double money;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
