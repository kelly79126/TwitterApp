package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.dialogfragment.ComposeDialogFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.ComposeDialogListener{

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline(aTweets.getItem(aTweets.getCount()-1).getUid() - 1);
                return true;
            }
        });

        client = TwitterApplication.getRestClient();
        populateTimeline(-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                showComposeDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showComposeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance("Compose");
        composeDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishComposeDialog(String status) {
        updateStatus(status);
    }

    private void populateTimeline(long maxId) {
        client.getHomeTimeLine(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                aTweets.addAll(Tweet.fromJsonArray(response));
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Kelly onFailure", errorResponse.toString());
            }
        });
    }

    private void updateStatus(String status) {
        client.updateStatus(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                aTweets.insert(Tweet.fromJson(response), 0);
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Kelly onFailure", errorResponse.toString());
            }
        });
    }
}
