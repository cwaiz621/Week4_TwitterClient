package com.codepath.apps.mysimpletweets;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "xxxxxxxx";       // Change this
    public static final String REST_CONSUMER_SECRET = "xxxxxx"; // Change this // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
    //METHOD == Endpoint
    //Home timeline - gets us the home timeline
    //Endpoints:
    //		-Get the home timeline for the user
    //GET statuses/home_timeline.json
    //		count = 25
    //since_id=1 (return all tweets sorted by the most recent)

    //Define end point
    public void getHomeTimeline(AsyncHttpResponseHandler handler, long maxId) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        //specify the params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (maxId == 0) {
            params.put("since_id", 1);
        } else {
            params.put("max_id", maxId);
        }
        //execute the request --reading so get request method, if writing post
        getClient().get(apiUrl, params, handler);
    }

    public void getCurrentUser(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        params.put("include_entities", true);
        getClient().get(apiUrl, params, handler);
    }


    public void postNewTweet(String data, AsyncHttpResponseHandler asyncHandler) {
        String apiUrl = getApiUrl("statuses/update.json");

        RequestParams reqParams = new RequestParams();
        reqParams.put("status", data);
        String url = client.getUrlWithQueryString(true, "https://api.twitter.com/1.1/statuses/update.json", reqParams);
        getClient().post(apiUrl, reqParams, asyncHandler);


    }

    public void getMentionTimeline(JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mention_timeline.json");
        //specify the params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        //execute the request --reading so get request method, if writing post
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenname, AsyncHttpResponseHandler handler){
        String url =getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("screen_name", screenname);
        //execute the request --reading so get request method, if writing post
        getClient().get(url, params, handler);

    }

    public void getUserProfile(String screenname, AsyncHttpResponseHandler handler){
        String url =getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenname);

        //execute the request --reading so get request method, if writing post
        getClient().get(url, params, handler);

    }






	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}