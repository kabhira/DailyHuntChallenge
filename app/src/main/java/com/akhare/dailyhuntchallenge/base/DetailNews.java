package com.akhare.dailyhuntchallenge.base;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akhare.dailyhuntchallenge.Network.NewsData;
import com.akhare.dailyhuntchallenge.Network.VolleyNetwork;
import com.akhare.dailyhuntchallenge.R;
import com.akhare.dailyhuntchallenge.data.DataManager;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class DetailNews extends Activity implements View.OnClickListener {

    private NewsData newsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        newsData = getIntent().getParcelableExtra("NewsData");
        Button expandButton = (Button) findViewById(R.id.detail_expand);
        Button bookmarkButton = (Button) findViewById(R.id.detail_bookmark);
        Button linkButton = (Button) findViewById(R.id.detail_link);
        Button shareButton = (Button) findViewById(R.id.detail_share);
        expandButton.setOnClickListener(this);
        bookmarkButton.setOnClickListener(this);
        linkButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        TextView titleTextView = (TextView) findViewById(R.id.detail_title);
        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.cell_imageView);
        TextView textView = (TextView) findViewById(R.id.news_detail_textView);
        titleTextView.setText(newsData.getTitle());
        ImageLoader imageLoader = VolleyNetwork.getInstance(CustomApplication.getAppContext()).getImageLoader();
        imageView.setImageUrl(newsData.getImage(), imageLoader);
        textView.setText(newsData.getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_expand:
                findViewById(R.id.upper_section).setVisibility(View.GONE);
                break;
            case R.id.detail_bookmark:
                DataManager.instance().bookmarkedSet.add(newsData);
                break;
            case R.id.detail_link:
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsData.getUrl()));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.detail_share:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        View uperView = findViewById(R.id.upper_section);
        if(uperView.getVisibility() == View.GONE){
            uperView.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_news, menu);
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
