package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.adapter.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.tweet;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {

    private TwitterClient client;
    private ArrayList<tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private long maxId=0;
//    private newPostModel newPost;
    private int REQUEST_TWEET_CODE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTweets = (ListView)findViewById(R.id.lvTweets);
        //create arrayList
        tweets = new ArrayList<tweet>();
        //construct the adapter from the data source
        aTweets=new TweetsArrayAdapter(this, tweets);
        //connect adapter with listview
        lvTweets.setAdapter(aTweets);
        //get the client
        client = new TwitterApp().getRestClient(); //giving us back a singleton client
        //place endlessScrollListener

        populateTimeline();


        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                populateTimeline();
                // or customLoadMoreDataFromApi(totalItemsCount);
                //return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });



    }


    //Send API request to get the timeline from json
    //Fill the listview by creating tweet objects from JSON
    public void populateTimeline(){

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Debug", response.toString());

                //deserialize json
                //create models
                //load model data into listview
                aTweets.addAll(tweet.fromJsonArray(response));
                maxId = tweet.getLowestId();
            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        }, maxId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.icn_compose) {

            //1.Navigate to filter activity
            //create intent
            Intent i = new Intent(this, Compose_activity.class);
            //run start activity method
            startActivityForResult(i, REQUEST_TWEET_CODE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Get result out of form
        if(REQUEST_TWEET_CODE == requestCode){
            if(resultCode==RESULT_OK){
                //need to cast
                tweet composedTweet= (tweet)data.getSerializableExtra("composedTweet");

                if(composedTweet!=null)
                {
                    aTweets.insert(composedTweet,0);
                }
            }
        }
    }


  /*  public void postTimeline(String msg){
        client.postNewTweet(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                tweet composedTweet = tweet.fromJson(response);
                aTweets.add(composedTweet);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }

            ;
        }, msg);
    }*/
}
