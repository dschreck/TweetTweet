package com.codepath.apps.tweetapp;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {

	Intent data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void cancelCompose(View v) {
		Intent data = new Intent();
		data.putExtra("tweet", "");
		setResult(RESULT_OK,data);
		finish();
	}
	
	public void saveTweet(View v) {
		Toast.makeText(this, "Tweet Saved", Toast.LENGTH_LONG).show();
		
		EditText etTweet = (EditText) findViewById(R.id.etBody);
		
		
		TwitterClient client = TwitterClientApp.getRestClient();
		data = new Intent();
		
		
		client.postTweet(etTweet.getText().toString(), new JsonHttpResponseHandler() {
			  public void onSuccess(JSONArray json){
				  data.putExtra("tweet", json.toString());
				}
		});
		
		setResult(RESULT_OK,data);
		finish();
	}

}
