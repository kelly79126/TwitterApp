package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {

    TextView tvDetailName;
    TextView tvDetailScreenName;
    ImageView ivDetailProfileImage;
    TextView tvDetailBody;
    TextView tvDetailRelativeTime;
    Tweet tweet;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));

        tvDetailName = (TextView) findViewById(R.id.tvDetailUserName);
        tvDetailScreenName = (TextView) findViewById(R.id.tvDetailScreenName);
        ivDetailProfileImage = (ImageView) findViewById(R.id.ivDetailProfile);
        tvDetailBody = (TextView) findViewById(R.id.tvDetailBody);
        tvDetailRelativeTime = (TextView) findViewById(R.id.tvDetailRelativeTime);

        tvDetailName.setText(user.name);
        tvDetailScreenName.setText("@" + user.screenName);
        tvDetailBody.setText(tweet.body);
        tvDetailRelativeTime.setText(tweet.createdAt);
        Glide.with(this).load(user.profileImageUrl).into(ivDetailProfileImage);
    }
}
