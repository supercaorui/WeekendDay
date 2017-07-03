package com.example.administrator.weekendday.api;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Api {
    private  static String url ="http://api.lanrenzhoumo.com/wh/common/leo_detail";
    private static String session_id = "00004044ec6f4f5b17e1f5a135802541c659d9";

    public static String getStarurl(int page){
        return "http://api.lanrenzhoumo.com/main/recommend/index?session_id=000040a0ebdb85f662896249fc160c89464d75&v=6&page="+page+"&lon=114.436951&lat=30.449234 ";
    }
    public static String getInfoUrl(int id){
        return url +"?leo_id="+id+ "&session_id="+session_id+"&v=4";
    }
}
