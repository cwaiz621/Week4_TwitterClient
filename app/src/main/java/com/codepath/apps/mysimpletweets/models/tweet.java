package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Crystal on 9/27/15.
 */
public class tweet implements Serializable{

    //pares the json
    //store the data
    //encapsulate state logic or display logic of data

    private String body;
    private long uid;//database id for the tweet
    private User user;//store embedded user object
    private String Timestamp;
    private String inReplyto;


    private static long lowestId = 0;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public User getUser() {
        return user;
    }

    public static long getLowestId() {
        return lowestId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public static void setLowestId(long lowestId) {
        tweet.lowestId = lowestId;
    }

    //Deserialize the JSONobject
    //Tweet.fromJson("{...}") -><tweet>
    public static tweet fromJson(JSONObject jsonObject){
        tweet Tweet = new tweet();
        //extract values from JSON
        //store them and return tweet object
        try {
            Tweet.body=jsonObject.getString("text");
            if(lowestId == 0) {
                lowestId = jsonObject.getLong("id");
            } else {
                if(lowestId > jsonObject.getLong("id"))
                    lowestId = jsonObject.getLong("id");
            }
                Tweet.uid =jsonObject.getLong("id");
            Tweet.user=User.fromJson(jsonObject.getJSONObject("user"));
            Tweet.Timestamp=jsonObject.getString("created_at");
            Tweet.inReplyto=jsonObject.getString("in_reply_to_user_id_str");
        } catch (JSONException e) {


            e.printStackTrace();
        }


        return Tweet;

    }

    //pass in array of json items and give us an array of tweets
    public static ArrayList<tweet> fromJsonArray(JSONArray jsonArray){
        ArrayList<tweet> tweetArray = new ArrayList<tweet>();
        //iterate JSON array and create tweets
        for (int i = 0; i<jsonArray.length(); i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                tweet Tweet = tweet.fromJson(tweetJson);
                if (Tweet !=null)
                    tweetArray.add(Tweet);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }


        return tweetArray;
    }
}
