package com.codepath.apps.tweetapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.tweetapp.TwitterClient;
import com.codepath.apps.tweetapp.TwitterClientApp;
import com.codepath.apps.tweetapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	TweetsListFragment fragmentTweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
		
		// SomeActivity.java
		TwitterClient client = TwitterClientApp.getRestClient();
		client.getHomeTimeline(1, new JsonHttpResponseHandler() {
		  public void onSuccess(JSONArray json) {
			  ArrayList<Tweet> tweets = Tweet.fromJson(json);
			  getAdapter().addAll(tweets);
		  }
		});
	}
	
	
}
