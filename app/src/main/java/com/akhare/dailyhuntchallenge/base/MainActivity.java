package com.akhare.dailyhuntchallenge.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.akhare.dailyhuntchallenge.Network.EventBusSingleton;
import com.akhare.dailyhuntchallenge.Network.NewsData;
import com.akhare.dailyhuntchallenge.Network.ViewUpdateEvent;
import com.akhare.dailyhuntchallenge.R;
import com.akhare.dailyhuntchallenge.data.DataManager;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {

    private ArrayList<NewsData> mDataset;
    private NewsAdapter mAdapter;
    private ArrayAdapter<String> spinnerAdapter;
    private String spinnerItem = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mDataset = new ArrayList<NewsData>();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewsAdapter(mDataset, this);
        mRecyclerView.setAdapter(mAdapter);

        DataManager dataManager = DataManager.instance();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList spinnerList = new ArrayList();
        spinnerList.add("ALL");
        spinnerList.addAll(dataManager.catagoryMap.keySet());
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, R.id.spinner_textView, spinnerList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerItem = parent.getAdapter().getItem(position).toString();
                update(spinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        findViewById(R.id.bookmark_button).setOnClickListener(this);

        update(spinnerItem);
        //NewsFeedRequest newsFeedRequest = new NewsFeedRequest();
        //VolleyNetwork.getInstance(this.getApplicationContext()).addToRequestQueue(newsFeedRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusSingleton.instance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusSingleton.instance().unregister(this);
    }

    @Subscribe
    public void updateView(ViewUpdateEvent data){
        update(spinnerItem);
    }

    private void update(String key){
        mDataset.clear();
        if(key.equals("ALL")){
            mDataset.addAll(DataManager.instance().getNewsList());
        }
        else if(key.equals("Bookmark")){
            mDataset.addAll(DataManager.instance().bookmarkedSet);
            findViewById(R.id.top_section).setVisibility(View.GONE);
        }
        else {
            mDataset.addAll(DataManager.instance().catagoryMap.get(key));
        }
        mAdapter.notifyDataSetChanged();

        spinnerAdapter.clear();
        spinnerAdapter.add("ALL");
        spinnerAdapter.addAll(DataManager.instance().catagoryMap.keySet());
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bookmark_button:
                update("Bookmark");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        View uperView = findViewById(R.id.top_section);
        if(uperView.getVisibility() == View.GONE){
            uperView.setVisibility(View.VISIBLE);
            update("ALL");
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
