package com.codepath.apps.tweetapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.tweetapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	TweetsAdapter adapter;

	public static final int REQUEST_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		// SomeActivity.java
		TwitterClient client = TwitterClientApp.getRestClient();
		client.getHomeTimeline(1, new JsonHttpResponseHandler() {
		  public void onSuccess(JSONArray json) {
			  ArrayList<Tweet> tweets = Tweet.fromJson(json);
			  ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
			  adapter = new TweetsAdapter(getBaseContext(), tweets);
			  lvTweets.setAdapter(adapter);
			  lvTweets.setOnScrollListener(new EndlessScrollListener() {
				
				@Override
				public void onLoadMore(int page, int totalItemsCount) {
					// TODO Auto-generated method stub
					loadMoreTweets(page);
				}
			});
		  }
		});
	}
	
	public void launchComposeView(MenuItem miItem) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		i.putExtra("something", "foobar");
		startActivityForResult(i, REQUEST_CODE);
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

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above

		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//		  String jsonString = data.getExtras().getString("tweet");
//		  Log.d("DEBUG","I am here at jsonString");
//		  if(jsonString != "") {
//			  try {
//				JSONObject jsonObj = new JSONObject(jsonString.toString());
				adapter.clear();
				//adapter.add(Tweet.fromJson(jsonObj));
				loadMoreTweets(-19);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		  //}
	  }
	} 

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
