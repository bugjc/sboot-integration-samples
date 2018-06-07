package com.bugjc.grocery;

/**
 * @Auther: qingyang
 * @Date: 2018/6/6 16:24
 * @Description:
 */
public class GlobalKeyConstants {

    /**每天抽奖总次数**/
    public static String ACTIVITY_DRAW_COUNT_KEY = "1activity:draw:total";

    public static String getActivityDrawCountKey() {
        return ACTIVITY_DRAW_COUNT_KEY;
    }

    /**每天奖品池总数**/
    public static String ACTIVITY_EVERYDAY_AWARD_TOTAL_KEY = "1activity:everyday:award:total";

    public static String getActivityEverydayAwardTotalKey() {
        return ACTIVITY_EVERYDAY_AWARD_TOTAL_KEY;
    }

    /**会员抽奖结果查询**/
    public static String ACTIVITY_DRAW_RESULT_QUERY_KEY = "activity:draw:query_result:";

    public static String getActivityDrawResultQueryKey(String queryId) {
        return ACTIVITY_DRAW_RESULT_QUERY_KEY+queryId;
    }

    /**每天会员抽奖次数**/
    public static String ACTIVITY_EVERYDAY_USER_DRAW_COUNT_KEY = "activity:everyday:user:draw:count:";

    public static String getActivityEverydayUserDrawCountKey(String userId) {
        return ACTIVITY_EVERYDAY_USER_DRAW_COUNT_KEY+userId;
    }

}
