package com.easyauth.common.constant;

/**
 * 数字常量
 */
public class NumberConstant {
    public static final long Day_Seconds = 86400;
    public static final long Week_Seconds = 604800;
    /**
     * <p>redis 与 当前服务器的通信延迟容错时间</p>
     * <p>token刷新过期机制是：
     * 判断redis内当前用户数据的过期时间 == jwt过期时间</p>
     * <p>考虑通信延迟判断相等时设置容错时间</p>
     * <p style="color:red">单位：毫秒</p>
     */
    public static final long Error_Time = 2000;

}
