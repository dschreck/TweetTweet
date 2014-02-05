package com.codepath.apps.tweetapp.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	private String body;
	private long uid;
	private boolean favorited;
	private boolean retweeted;
    private User user;
    private String createdAt;

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return uid;
    }

    public boolean isFavorited() {
        return favorited;
    }
    
    public String getCreatedAtDate() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
    	dateFormat.setLenient(false);
    	Date created = null;
    	try {
    		created = dateFormat.parse(createdAt);
    	} catch (Exception e) {
    		return null;
    	}
    	
    	// today
    	Date today = new Date();
    	
    	// how much time since (ms)
    	Long duration = today.getTime() - created.getTime();
    	
    	int second = 1000;
    	int minute = second * 60;
    	int hour = minute * 60;
    	int day = hour * 24;
    	
    	if (duration < second * 7) {
    		return "right now";
    	}
    	if (duration < minute) {
    		int n = (int) Math.floor(duration / second);
    		return n +" seconds ago";
    	}
    	if (duration < minute * 2) {
    		return "about 1 minute ago";
    	}
    	if (duration < hour) {
			int n = (int) Math.floor(duration / minute);
			return n + " minutes ago";
		}
 
		if (duration < hour * 2) {
			return "about 1 hour ago";
		}
 
		if (duration < day) {
			int n = (int) Math.floor(duration / hour);
			return n + " hours ago";
		}
		if (duration > day && duration < day * 2) {
			return "yesterday";
		}
 
		if (duration < day * 365) {
			int n = (int) Math.floor(duration / day);
			return n + " days ago";
		} else {
			return "over a year ago";
		}
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
        	tweet.body = jsonObject.getString("text");
        	tweet.uid = jsonObject.getLong("id");
        	tweet.favorited = jsonObject.getBoolean("favorited");
        	tweet.retweeted = jsonObject.getBoolean("retweeted");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
           
           
            tweet.createdAt = jsonObject.getString("created_at");
            
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
}