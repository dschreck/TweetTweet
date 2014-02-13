package com.codepath.apps.tweetapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.tweetapp.TwitterClient;
import com.codepath.apps.tweetapp.TwitterClientApp;
import com.codepath.apps.tweetapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;

public class MentionsFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		TwitterClient client = TwitterClientApp.getRestClient();
		client.getMentions(1, new JsonHttpResponseHandler() {
		  public void onSuccess(JSONArray json) {
			  ArrayList<Tweet> tweets = Tweet.fromJson(json);
			  getAdapter().addAll(tweets);
		  }
		});
	}
}
