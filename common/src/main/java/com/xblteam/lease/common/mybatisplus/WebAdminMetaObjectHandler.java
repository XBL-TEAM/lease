package com.xblteam.lease.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WebAdminMetaObjectHandler implements MetaObjectHandler {   //拦截器
    @Override
    public void insertFill(MetaObject metaObject) {     //插入时填充
        strictInsertFill(metaObject,"createTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {     //更新时填充
        strictUpdateFill(metaObject,"updateTime", Date.class, new Date());
    }
}
