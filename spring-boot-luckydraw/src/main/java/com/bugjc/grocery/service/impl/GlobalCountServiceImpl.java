package com.bugjc.grocery.service.impl;

import com.bugjc.grocery.service.GlobalCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: qingyang
 * @Date: 2018/6/5 22:52
 * @Description:
 */
@Service
public class GlobalCountServiceImpl implements GlobalCountService {

    @Autowired
    private UserDrawCountComponent userDrawCountComponent;
    @Autowired
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

        Integer value = 200;//200个奖品
        AwardSinkComponent.init(value);
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
