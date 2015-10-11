package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.AbsListView;

import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Crystal on 10/7/15.
 */
public class MentionsTimelineFragment extends TweetListFragment {

    private TwitterClient client;
    private long maxId=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the client
        client = TwitterApp.getRestClient(); //giving us back a singleton client
        populateTimeline();
    }

    @Override
    public void initiateRefresh(){
        maxId=0;

        populateTimeline();
    }
    //Send API request to get the timeline from json
    //Fill the listview by creating tweet objects from JSON
    public void populateTimeline(){

        client.getMentionTimeline(new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Debug", response.toString());

                //deserialize json
                //create models
                //load model data into listview
                addAll(tweet.fromJsonArray(response));
                maxId = tweet.getLowestId();
            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        });
    }

}
