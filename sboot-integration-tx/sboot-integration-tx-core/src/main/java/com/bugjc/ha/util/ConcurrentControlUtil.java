package com.bugjc.ha.util;

/**
 * 业务并发控制类
 * @author aoki
 * @create 2018-03-23
 */
public class ConcurrentControlUtil {


    /**
     * 访问频次控制
     * 对程序中拥有唯一标识的用户进行限流，当未超过设置的时间会返回true，否则false.
     * 注意：为了避免业务线业务方法key冲突，在key的设计上应该据多个相对唯一的标志性；
     * 例如：用户业务线（user）+上传图片（u-image）+限制对象标识（userId = 10001）:user-upload-image-10001
     * @param key
     * @param timeout DateUnit.class
     * @return
     */
    public static boolean visitFrequency(String key,long timeout){
        key = "VF"+key;
        if (MyCacheUtil.timedCache.containsKey(key)){
            return true;
        }else {
            MyCacheUtil.timedCache.put(key, key, timeout);
            return false;
        }
    }

}
