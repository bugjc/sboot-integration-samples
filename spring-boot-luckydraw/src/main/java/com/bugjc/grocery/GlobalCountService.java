package com.bugjc.grocery;

/**
 * 全局计数
 * @Auther: qingyang
 * @Date: 2018/6/5 22:41
 * @Description:
 */
public interface GlobalCountService {

    /**
     * 活动抽奖次数
     * @return
     */
    int drawCount(boolean isDel);

    /**
     * 活动总奖品池计数
     * @return
     */
    int awardSinkCount(boolean isDel);

    /**
     * 活动某个赠品总计数
     * @return
     */
    int awardSinkCountByGiftId(String giftId);

    /**
     * 用户每日抽奖次数
     * @return
     */
    int everyDayUserDrawCountByUserId(String userId);
}
