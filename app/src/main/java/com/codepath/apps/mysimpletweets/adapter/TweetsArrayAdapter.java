package com.codepath.apps.mysimpletweets.adapter;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.Activities.Compose_activity;
import com.codepath.apps.mysimpletweets.Activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.tweet;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Crystal on 9/27/15.
 */

//take the tweet objects, and turn them into views displayed in the list
public class TweetsArrayAdapter  extends ArrayAdapter<tweet>{


        public TweetsArrayAdapter(Context context, List<tweet> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

    //Overide and setup custom adapter


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the tweet
        tweet Tweet = getItem(position);

        //find and inflate temlate view
        //view holder pattern
        if (convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);

        //find the subview to fill with data in the template
        ImageView iv_profilePic =(ImageView) convertView.findViewById(R.id.iv_profilePic);
        TextView tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);
        TextView tv_body = (TextView) convertView.findViewById(R.id.tv_body);
        TextView tv_relativeTimeStamp = (TextView) convertView.findViewById(R.id.tvRelativeTimestamp);
        //populate data into the subviews
        tv_userName.setText(Tweet.getUser().getScreenName());
        tv_body.setText(Tweet.getBody());
        String relativeTime = getRelativeTime(Tweet.getTimestamp());
        tv_relativeTimeStamp.setText(relativeTime);

        iv_profilePic.setTag(Tweet.getUser().getScreenName());
        //erase data in image
        iv_profilePic.setImageResource(android.R.color.transparent);//clear image for recycled view
        Picasso.with(getContext()).load(Tweet.getUser().getProfileImageUrl()).into(iv_profilePic);

        iv_profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String screenName = v.getTag().toString();
                Toast.makeText(getContext(), screenName, Toast.LENGTH_SHORT).show();

                //create intent
                Intent i = new Intent(getContext(), ProfileActivity.class);
                //run start activity method
                i.putExtra("screen_name",screenName);
//                Intent i = new Intent(this, ProfileActivity.class);/
                i.putExtra("currentProfile", false);
                v.getContext().startActivity(i);
            }
        });
        //return view
        return convertView;
    }

    public String getRelativeTime(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

   /* public String getRelativeTime(String time){
        long TweetTime = Date.parse(time);
        long unixTime = System.currentTimeMillis();
        int secondsPast= (int)(unixTime - TweetTime) /1000;

        if(secondsPast < 60){
            return String.valueOf(secondsPast + "s");
        }else{
            if(secondsPast < 3600){
                return String.valueOf(secondsPast/60 + "m");
            }else{
                if(secondsPast <= 86400){
                    return String.valueOf(secondsPast/3600 + "h");
                }else{
                    if(secondsPast > 86400){
                        return String.valueOf(secondsPast/86400)+"days";
                    }}}}

        return String.valueOf(secondsPast + "error");

    }*/
}
