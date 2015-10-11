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
 * Created by Crystal on 10/8/15.
 */
public class UserTimelineFragment extends TweetListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the client
        client = TwitterApp.getRestClient(); //giving us back a singleton client
        populateTimeline();
    }

        // Creates a new fragment given an int and title
        // UserTimline.newInstance("screenname");
        public static UserTimelineFragment newInstance(String screenname) {
            UserTimelineFragment userFragment = new UserTimelineFragment();
            Bundle args = new Bundle();
            //set as an argument from Profile activity class
            args.putString("screen_name",screenname);
            userFragment.setArguments(args);
            return userFragment;
        }

    @Override
    public void initiateRefresh(){
        populateTimeline();
    }


    //Send API request to get the timeline from json
    //Fill the listview by creating tweet objects from JSON
    public void populateTimeline(){
        //retrieved as argument here from new instance method
        String screenname = getArguments().getString("screen_name");
        client.getUserTimeline(screenname, new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Debug", response.toString());

                //deserialize json
                //create models
                //load model data into listview
                addAll(tweet.fromJsonArray(response));
            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        });
    }


}
