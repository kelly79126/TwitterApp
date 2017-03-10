package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.SmartFragmentStatePagerAdapter;
import com.codepath.apps.mysimpletweets.fragments.ComposeDialogFragment;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.ComposeDialogListener{

    ViewPager vpPager;
//    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // get viewpager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // attach the tabstrip to the viewer
        tabStrip.setViewPager(vpPager);

//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_timeline);
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                fetchTimelineAsync(0);
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//
//        lvTweets = (ListView) findViewById(R.id.lvTweets);
//        tweets = new ArrayList<>();
//        aTweets = new TweetsArrayAdapter(this, tweets);
//        lvTweets.setAdapter(aTweets);
//        lvTweets.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount) {
//                populateTimeline(aTweets.getItem(aTweets.getCount()-1).getUid() - 1);
//                return true;
//            }
//        });
//        setListViewListener();
    }

//    private void setListViewListener(){
//        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(TimelineActivity.this, TweetDetailActivity.class);
//                i.putExtra("tweet", Parcels.wrap(aTweets.getItem(position)));
//                i.putExtra("user", Parcels.wrap(aTweets.getItem(position).getUser()));
//                startActivity(i);
//            }
//        });
//    }

//    public void fetchTimelineAsync(int page) {
//        // Send the network request to fetch the updated data
//        // `client` here is an instance of Android Async HTTP
//        // getHomeTimeline is an example endpoint.
//        client.getHomeTimeLine(-1, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
////                homeTimelineFragment.clear();
//                homeTimelineFragment.addAll(Tweet.fromJsonArray(response));
////                homeTimelineFragment.notifyDataSetChanged();
////                swipeContainer.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("DEBUG", "Fetch timeline error: " + errorResponse);
//            }
//        });
//    }

    public void onProfileView(MenuItem item) {
        // launch a profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
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
//        HomeTimelineFragment homeFragment = (HomeTimelineFragment) getSupportFragmentManager().findFragmentByTag("twitterHomePage");
//        if(null != homeFragment) {
//            homeFragment.updateStatus(status);
//        }else{
//            Log.e("Kelly", "it is null");
//        }
        TweetsPagerAdapter tweetsPagerAdapter = (TweetsPagerAdapter) vpPager.getAdapter();
        HomeTimelineFragment homeFragment = (HomeTimelineFragment) tweetsPagerAdapter.getRegisteredFragment(0);
        homeFragment.updateStatus(status);
    }

    //return the order of the fragment in the view pager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};

        //Adapter gets the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        //The order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            Log.e("Kelly", "getItem TEST");
            if(position ==0){
//                HomeTimelineFragment homeFragment = new HomeTimelineFragment();
//                getSupportFragmentManager().beginTransaction().add(homeFragment, "twitterHomePageXX").commit();
//                return homeFragment;
                return new HomeTimelineFragment();
            }else if(position ==1){
                return new MentionsTimelineFragment();
            }else{
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}
