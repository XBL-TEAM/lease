package com.xblteam.lease.web.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xblteam.lease.model.entity.BrowsingHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xblteam.lease.web.app.vo.history.HistoryItemVo;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface BrowsingHistoryService extends IService<BrowsingHistory> {

    IPage<HistoryItemVo> pageHistoryItemByUserId(long current, long size, Long userId);

    void saveHistory(Long userId, Long id);
}
