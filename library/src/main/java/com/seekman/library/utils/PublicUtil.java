package com.seekman.library.utils;

/**
 * Created by chen-gui on 16-5-26.
 *
 * 公共工具类
 */
public class PublicUtil {

    /**这里写项目测试的主机**/
    /**请求实际api时，在HOST + 项目名/Action名称/参数**/
    /**例如    HOST/SeekMan/MobileLogin/?user=haha&password=123456   **/

    public static final String HOST = "http://172.30.84.10:8082";

    /**获取主题分类**/
    public static final String THEME_CATEGORY = HOST + "/AndroidServer/themegson";
    /**获取活动条目**/
    public static final String THEME_ITEM = HOST + "/AndroidServer/sendactivitygson?theme_name=";
    /**发布活动**/
    public static final String PUBLISH_ACTIVITY = HOST + "/AndroidServer/activityservlet";




}
