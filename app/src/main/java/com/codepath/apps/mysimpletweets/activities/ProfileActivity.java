package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {
//    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));

        if(savedInstanceState == null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(user.screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }

        getSupportActionBar().setTitle("@" + user.screenName);
        populateProfileHeader(user);

//        client = TwitterApplication.getRestClient();

//        client.getUserInfo(new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("Kelly", response.toString());
//                user = User.fromJson(response);
//                getSupportActionBar().setTitle("@" + user.screenName);
//                populateProfileHeader(user);
//            }
//        });

    }

    private void populateProfileHeader(User user) {

        Log.d("Kelly", user.screenName);
        Log.d("Kelly", user.taglines);
        Log.d("Kelly", ""+user.followersCount);
        Log.d("Kelly", ""+user.followingsCount);


        TextView tvName = (TextView) findViewById(R.id.tvUserName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollingers = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);

        tvName.setText(user.name);
        tvTagline.setText(user.taglines);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollingers.setText(user.followingsCount + " Following");
        Glide.with(this).load(user.profileImageUrl).into(ivProfile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

        return super.onOptionsItemSelected(item);
    }
}
