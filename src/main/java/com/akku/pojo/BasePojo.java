package com.akku.pojo;

import org.nutz.dao.entity.annotation.Column;

import java.util.Date;

/**
 * Created by akku on 2017/6/15.
 */
public abstract class BasePojo {

    @Column("ct")
    protected Date createTime;
    @Column("ut")
    protected Date updateTime;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}