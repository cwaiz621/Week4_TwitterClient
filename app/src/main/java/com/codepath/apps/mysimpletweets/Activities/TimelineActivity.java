package com.codepath.apps.mysimpletweets.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.adapter.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetListFragment;
import com.codepath.apps.mysimpletweets.models.tweet;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {


    private TweetListFragment fragmenttweetlist;



//    private newPostModel newPost;
    private int REQUEST_TWEET_CODE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


       // if(isNetworkAvailable()) {
            //get the view pager
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            //set the view pager adapter for the pager
            viewPager.setAdapter(new TweetPagerAdapter(getSupportFragmentManager()));
            //find the sliding tabstrip
            PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

            //attach the viewpager to the tabstrip
            tabStrip.setViewPager(viewPager);
       /// }else{
          //  Toast.makeText(this, "no internet", Toast.LENGTH_SHORT).show();}

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

    public void onProfileView(MenuItem item) {
        //Launch Profile view
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("currentProfile", true);
        startActivity(i);

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

                    fragmenttweetlist.getAdapter().insert(composedTweet,0);
                }
            }
        }
    }
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


public class TweetPagerAdapter extends FragmentStatePagerAdapter {
      private String tabTitles [] = {"Home","Mentions"};



//Adapter gets manager which inserts or removes frament from activity
      public TweetPagerAdapter(android.support.v4.app.FragmentManager fm){
          super(fm);
  }
//the order and creation of fragments within the pager
      @Override
      public Fragment getItem(int position) {
          if(position==0){
          return new HomeTimelineFragment();}else
              if(position==1){
                  return new MentionsTimelineFragment();}else{
                  return null;
          }
      }
//return the tab titles
      @Override
      public CharSequence getPageTitle(int position) {
          return tabTitles[position];
      }
// how many fragments there are to swipe between
      @Override
      public int getCount() {
          return tabTitles.length;
      }
  }

}
