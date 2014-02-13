package com.codepath.apps.tweetapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.tweetapp.fragments.HomeTimelineFragment;
import com.codepath.apps.tweetapp.fragments.MentionsFragment;
import com.codepath.apps.tweetapp.fragments.TweetsListFragment;

public class TimelineActivity extends FragmentActivity implements TabListener{
	TweetsAdapter adapter;
	TweetsListFragment fragmentTweets;

	public static final int REQUEST_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
	}
	
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home).setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsTimelineFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
		
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}

	public void launchComposeView(MenuItem miItem) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		i.putExtra("something", "foobar");
		startActivityForResult(i, REQUEST_CODE);
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
				//loadMoreTweets(-19);
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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		
		if(tab.getTag() == "HomeTimelineFragment") {
			// set fragment to home
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		} else {
			// set fragment to mentions
			fts.replace(R.id.frame_container, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
