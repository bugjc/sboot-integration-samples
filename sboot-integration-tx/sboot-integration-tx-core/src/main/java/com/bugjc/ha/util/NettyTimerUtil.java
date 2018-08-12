package com.bugjc.ha.util;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * Hash Wheel Timer 使用
  * @author  作者 E-mail: qingmuyi@foxmail.com
  * @date 创建时间：2017年3月22日 上午9:27:27 
  * @version 1.0
 */
public class NettyTimerUtil {
	
	private NettyTimerUtil(){}
	
	public static NettyTimerUtil getInstance() {
	    return NettyTimerTaskHolder.INSTANCE;
	}
	
	private static class NettyTimerTaskHolder {
	    private static final NettyTimerUtil INSTANCE = new NettyTimerUtil();
	}
	
	//初始化1000个格子并以每1秒执行一个格子	
    static Timer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 1000); 
    
    //根据任务的过期时间计算出格子位置并添加(分钟)
    public static Timeout addTask(TimerTask task,int expTime){   
        return timer.newTimeout(task, expTime, TimeUnit.MINUTES);  
    } 
    
    //根据任务的过期时间计算出格子位置并添加(自定义)
    public static Timeout addTask(TimerTask task,int expTime,TimeUnit timeUnit){   
        return timer.newTimeout(task, expTime, timeUnit);  
    } 
    
}
