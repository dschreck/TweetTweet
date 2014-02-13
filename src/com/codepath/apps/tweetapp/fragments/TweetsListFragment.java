package com.codepath.apps.tweetapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.tweetapp.EndlessScrollListener;
import com.codepath.apps.tweetapp.R;
import com.codepath.apps.tweetapp.TweetsAdapter;
import com.codepath.apps.tweetapp.TwitterClient;
import com.codepath.apps.tweetapp.TwitterClientApp;
import com.codepath.apps.tweetapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsListFragment extends Fragment {
	TweetsAdapter adapter;
	
	@Override 
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragments_tweet_list, parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		ListView lvTweets = (ListView)getActivity().findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				loadMoreTweets(page);
			}
		});
	}
	

	public void loadMoreTweets(int offset) {
		TwitterClient client = TwitterClientApp.getRestClient();
		client.getHomeTimeline(offset+20, new JsonHttpResponseHandler() {
		  public void onSuccess(JSONArray json) {
			  ArrayList<Tweet> tweets = Tweet.fromJson(json);
			  adapter.addAll(tweets);
			}
		});
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
