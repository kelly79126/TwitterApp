package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.widgets.TwitterApplication;
import com.codepath.apps.mysimpletweets.widgets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kelly79126 on 2017/3/7.
 */

public class HomeTimelineFragment extends TweetsListFragment{
    private TwitterClient client;

    public interface ClickOtherProfileListener {
        void onClickOtherProfile(String status);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(-1);
    }

    public static HomeTimelineFragment newInstance() {
        HomeTimelineFragment frag = new HomeTimelineFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, parent, savedInstanceState);

    }

    @Override
    public void loadMoreItem() {
        populateTimeline(aTweets.getItem(aTweets.getCount()-1).getUid() - 1);
    }

    private void populateTimeline(long maxId) {
        Log.d("Kelly", "maxId: " + maxId);
        client.getHomeTimeLine(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJsonArray(response));
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public void updateStatus(String status) {
        client.updateStatus(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                aTweets.insert(Tweet.fromJson(response), 0);
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

}
