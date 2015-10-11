package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Crystal on 10/9/15.
 */
public class myprofilefragment extends Fragment {

    TwitterClient client;
    User user;
    TextView tvName;
    TextView tvTagline ;
    ImageView ivProfilepic;
    TextView tvFollowers;
    TextView tvFollowing;
    TextView tvNumberTweets;
    ImageView ivBgImage;

    //inflation logic

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        tvName = (TextView) v.findViewById(R.id.tv_Name);
        tvTagline = (TextView) v.findViewById(R.id.tv_Tagline);
        ivProfilepic = (ImageView) v.findViewById(R.id.ivProfileImage);
        ivBgImage = (ImageView)v.findViewById(R.id.ivbackgroundImage);
        tvFollowers = (TextView) v.findViewById(R.id.tv_follower);
        tvFollowing = (TextView)v.findViewById(R.id.tv_following);
        tvNumberTweets= (TextView)v.findViewById(R.id.tv_numberTweets);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient();

        //get the user account info
        client.getCurrentUser( new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJson(response);
                //my current account info
                populateProfileHeader(user);
            }
        });

    }


    public void populateProfileHeader(User user){


        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        String FollowersCount = String.valueOf(user.getFollowersCount());
        formatText(FollowersCount, "FOLLOWERS", tvFollowers);
        //tvFollowers.setText(user.getFollowersCount() + "FOLLOWERS");

        String FollowingCount = String.valueOf(user.getFollowingCount());
        formatText(FollowingCount, "FOLLOWING", tvFollowing);

        String TweetCount = String.valueOf(user.getTweetsCount());
        formatText(TweetCount, "TWEETS", tvNumberTweets);

        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfilepic);
      //  Picasso.with(getContext()).load(user.getBackgroundImageUrl()).into(ivBgImage);

    }

    public void formatText(String text1, String text2, TextView tv){
        // Create a span that will make the text red
        ForegroundColorSpan blackForegroundColorSpan = new ForegroundColorSpan(
                getResources().getColor(android.R.color.black));


        // Use a SpannableStringBuilder so that both the text and the spans are mutable
        SpannableStringBuilder ssb = new SpannableStringBuilder(text1);
        // Apply the color span
        ssb.setSpan(
                blackForegroundColorSpan,            // the span to add
                0,                                 // the start of the span (inclusive)
                ssb.length(),                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder
        // SPAN_EXCLUSIVE_EXCLUSIVE means to not extend the span when additional
        // text is added in later

        // Add a blank space
        ssb.append("\n");

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(20);

// Add the secondWord and apply the strikethrough span to only the second word
        ssb.append(text2);
        ssb.setSpan(
                sizeSpan,
                ssb.length() - text2.length(),
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set the TextView text and denote that it is Editable
// since it's a SpannableStringBuilder
        tv.setText(ssb, TextView.BufferType.EDITABLE);

    }

}
