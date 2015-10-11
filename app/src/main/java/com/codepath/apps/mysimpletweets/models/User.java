package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Crystal on 9/27/15.
 */
public class User{
    //list user attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String Tagline;
    private int followersCount;
    private int followingCount;
    private int tweetsCount;
    private String backgroundImageUrl;


    public void setName(String name) {
        this.name = name;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getTagline() {
        return Tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getTweetsCount() {
        return tweetsCount;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    //deserialize the user json into USer object
    public static User fromJson(JSONObject json){
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.Tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingCount = json.getInt("friends_count");
            u.tweetsCount = json.getInt("statuses_count");
            u.backgroundImageUrl = json.getString("profile_background_image_url");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
