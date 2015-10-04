package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.models.tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;


public class Compose_activity extends ActionBarActivity {
    TextView tv_user;
    EditText et_message;
    ImageView iv_profilePic;
//    Button btn_save;
    User currentUser;
    String screenName;
 //   SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_activity);
        client = new TwitterApp().getRestClient();
 //       btn_save = (Button) findViewById(R.id.btn_tweet);
        et_message = (EditText) findViewById(R.id.et_message);




        client.getCurrentUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentUser = new User();
                currentUser = User.fromJson(response);
                screenName = currentUser.getScreenName();
                //               Toast.makeText(getApplicationContext(), screenName, Toast.LENGTH_SHORT).show();

                tv_user = (TextView) findViewById(R.id.tv_user);
                iv_profilePic = (ImageView) findViewById(R.id.iv_profilePic);


                tv_user.setText(currentUser.getScreenName());
                iv_profilePic.setImageResource(android.R.color.transparent);//clear image for recycled view
                Picasso.with(getApplicationContext()).load(currentUser.getProfileImageUrl()).into(iv_profilePic);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if (id == R.id.btn_tweet) {

           onTweet (item);
           return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void onTweet(MenuItem mi) {
        String Tweet = et_message.getText().toString();
        if(!Tweet.equals("")){

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();Intent i = new Intent();

            client.postNewTweet(Tweet, ts, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                tweet composedTweet = tweet.fromJson(json);
                et_message.setText("");
                /*status=test&in_reply_to=cs480test2&oauth_version=1.0&oauth_nonce=XXXXXX&
                oauth_timestamp=1411668337&oauth_consumer_key=XXXXXXX&oauth_token=XXXXXXXX&
                oauth_signature_method=HMAC-SHA1&oauth_signature=XXXXX*/
                Intent i = new Intent();
                i.putExtra("composedTweet", composedTweet);
                // Package with result.
                setResult(RESULT_OK, i);
                // Dismiss the activity.
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray e) {
                Log.d("debug", e.toString());
                Log.d("debug", throwable.toString());
            }

        });}else{
            Toast.makeText(this,"No Tweet Submitted", Toast.LENGTH_SHORT).show();
        }}

}
