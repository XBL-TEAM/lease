package com.xblteam.lease.web.admin.schedule;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xblteam.lease.model.entity.LeaseAgreement;
import com.xblteam.lease.model.enums.LeaseStatus;
import com.xblteam.lease.web.admin.service.LeaseAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 */
@Component
public class ScheduleTask {

    @Autowired
    private LeaseAgreementService leaseAgreementService;

    /**
     * 租约状态的定时任务
     * 每天0点检查租约状态，如果过期则自动修改状态
     */
    @Scheduled(
            //每天执行
            cron = "0 0 0 * * *"       //cron表达式：seconds minutes hours day month week
    )
    public void checkLeaseAgreementStatus() {
        leaseAgreementService.update(new LambdaUpdateWrapper<LeaseAgreement>()
                .le(LeaseAgreement::getLeaseEndDate, new Date())        //结束时间小于当前时间的
                .in(LeaseAgreement::getStatus,2,5)              //状态为在租的
                .set(LeaseAgreement::getStatus, LeaseStatus.EXPIRED));  //修改状态为已过期

    }
}
