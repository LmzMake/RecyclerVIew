package com.walxy.recyclerview.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 作者：王兵洋  2017/8/24 14:35
 * 类的用途：
 */
public class Bean {

    public int code;
    public boolean success;
    public int height;
    public int width;
    public Object message;
    public List<DataBean> data;

    public static Bean objectFromData(String str) {

        return new com.google.gson.Gson().fromJson(str, Bean.class);
    }

    public static Bean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new com.google.gson.Gson().fromJson(jsonObject.getString(str), Bean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class DataBean {

        public String url;
        public long createTime;
        public String hxKey;
        public String img;
        public String yulin;
        public int star;
        public String title;
        public String occupation;
        public String userImg;
        public int impressEditId;
        public int impressType;
        public String introduction;
        public int replyTimes;
        public String remark;
        public int click;
        public int userAge;
        public long topTime;
        public int recommend;
        public int source;
        public String userName;
        public int reporter;
        public int status;
        public String content;

        public static DataBean objectFromData(String str) {

            return new com.google.gson.Gson().fromJson(str, DataBean.class);
        }

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new com.google.gson.Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
