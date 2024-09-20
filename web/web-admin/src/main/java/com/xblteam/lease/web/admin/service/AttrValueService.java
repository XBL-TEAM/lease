package com.xblteam.lease.web.admin.service;

import com.xblteam.lease.model.entity.AttrValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xblteam.lease.web.admin.mapper.AttrValueMapper;
import com.xblteam.lease.web.admin.mapper.RoomAttrValueMapper;
import com.xblteam.lease.web.admin.vo.attr.AttrValueVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_value(房间基本属性值表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface AttrValueService extends IService<AttrValue> {

    /**
     * 根据id查找AttrValue
     * @param attrId
     * @return
     */
    List<AttrValue> findById(Integer attrId);

    List<AttrValueVo> queryByRoomId(Long roomId);

}
