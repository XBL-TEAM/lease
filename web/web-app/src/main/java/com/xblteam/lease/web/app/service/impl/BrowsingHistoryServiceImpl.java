package com.xblteam.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xblteam.lease.model.entity.BrowsingHistory;
import com.xblteam.lease.web.app.mapper.BrowsingHistoryMapper;
import com.xblteam.lease.web.app.service.BrowsingHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.web.app.vo.history.HistoryItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {

    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;

    @Override
    public IPage<HistoryItemVo> pageHistoryItemByUserId(long current, long size, Long userId) {
        return browsingHistoryMapper.pageHistoryItemByUserId(new Page<>(current, size), userId);
    }

    @Async          //异步方法，开启新线程去执行该方法
    @Override
    public void saveHistory(Long userId, Long id) {

        //判断用户是否浏览过该房间
        BrowsingHistory browsingHistory = browsingHistoryMapper.selectOne(new LambdaQueryWrapper<BrowsingHistory>()
                .eq(BrowsingHistory::getUserId, userId)
                .eq(BrowsingHistory::getRoomId, id));

        //如果浏览过
        if (browsingHistory != null) {
            //更新浏览时间
            browsingHistory.setBrowseTime(new Date());
            browsingHistoryMapper.updateById(browsingHistory);
            return;
        }

        //如果没有浏览过
        browsingHistoryMapper.insert(new BrowsingHistory(userId,id,new Date()));
    }
}