package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.fragments.ProfileFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.myprofilefragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {

    TwitterClient client;
    User user;
    String myScreenName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApp.getRestClient();

        //get the user account info
        client.getCurrentUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJson(response);
                //my current account info
                getSupportActionBar().setTitle("@" + user.getScreenName());
                myScreenName = user.getScreenName().toString();
            }
        });


        //get the screen name
        String screenname = getIntent().getStringExtra("screen_name");
        boolean currentProfile = getIntent().getBooleanExtra("currentProfile", true);

        if(savedInstanceState==null) {
            //create user timeline fragment, screen name passed in new instance
            UserTimelineFragment FragmentUserTimeline = UserTimelineFragment.newInstance(screenname);
            //Display fragment from this activity (dynamically)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flcontainer, FragmentUserTimeline);
            ft.commit();//execute fragment change


            if(!currentProfile)
            {ProfileFragment profileFragment = ProfileFragment.newInstance(screenname);
            FragmentTransaction profileTransaction = getSupportFragmentManager().beginTransaction();
            profileTransaction.replace(R.id.flprofileframe, profileFragment);
            profileTransaction.commit();}
            else{

                FragmentTransaction MYprofileTransaction = getSupportFragmentManager().beginTransaction();
                MYprofileTransaction.replace(R.id.flprofileframe, new myprofilefragment());
                MYprofileTransaction.commit();

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
