package com.bugjc.cj.service.impl;

import com.bugjc.cj.service.GlobalCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: qingyang
 * @Date: 2018/6/5 22:52
 * @Description:
 */
@Service
public class GlobalCountServiceImpl implements GlobalCountService {

    @Resource
    private UserDrawCountComponent userDrawCountComponent;
    @Resource
    private AwardSinkComponent awardSinkComponent;

    @Override
    public int drawCount(boolean isDel) {

        if (isDel){
            userDrawCountComponent.removeCacheByUserDrawCount();
        }

        Integer value = 10;//10次抽奖机会

        return value;
    }

    @Override
    public int awardSinkCount(boolean isDel) {

        if (isDel){
            awardSinkComponent.removeCacheByAwardSink();
        }

        Integer value = 5;//5个奖品
        awardSinkComponent.init(value);
        return value;
    }

    @Override
    public int awardSinkCountByGiftId(String giftId) {
        return 0;
    }

    @Override
    public int everyDayUserDrawCountByUserId(String userId) {
        userDrawCountComponent.init(userId,this.drawCount(false));
        return userDrawCountComponent.decrementAndGet(userId);
    }
}
