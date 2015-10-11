package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.Activities.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapter.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Crystal on 10/7/15.
 */
public abstract class TweetListFragment extends Fragment {

    private ArrayList<tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private View v;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar progressBarFooter;



    //inflation logic

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        // Retrieve the SwipeRefreshLayout and ListView instances
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                initiateRefresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });




        //connect adapter with listview
        lvTweets.setAdapter(aTweets);


        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                populateTimeline();
            }
        });
        return v;

    }




    //creation life cycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create arrayList
        tweets = new ArrayList<>();
        //construct the adapter from the data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);


    }

    public void addAll(ArrayList<tweet> tweets) {
        aTweets.addAll(tweets);
    }
    public TweetsArrayAdapter getAdapter(){return aTweets;}

    protected abstract void populateTimeline();

    protected  abstract void initiateRefresh();


}


