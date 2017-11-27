package com.akhare.dailyhuntchallenge.data;

import com.akhare.dailyhuntchallenge.Network.Articles;
import com.akhare.dailyhuntchallenge.Network.EventBusSingleton;
import com.akhare.dailyhuntchallenge.Network.NewsData;
import com.akhare.dailyhuntchallenge.Network.NewsFeedRequest;
import com.akhare.dailyhuntchallenge.Network.ViewUpdateEvent;
import com.akhare.dailyhuntchallenge.Network.VolleyNetwork;
import com.akhare.dailyhuntchallenge.base.CustomApplication;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by akhare on 10/18/15.
 */
public class DataManager {

    private static  DataManager instance = new DataManager();
    private ArrayList<NewsData> newsList;
    public HashMap<String, LinkedList<NewsData>> catagoryMap;
    public HashSet<NewsData> bookmarkedSet;

    private DataManager(){
        newsList = new ArrayList<NewsData>();
        catagoryMap = new HashMap<String, LinkedList<NewsData>>();
        bookmarkedSet = new HashSet<NewsData>();

        EventBusSingleton.instance().register(this);
        NewsFeedRequest newsFeedRequest = new NewsFeedRequest();
        VolleyNetwork.getInstance(CustomApplication.getAppContext()).addToRequestQueue(newsFeedRequest);
    }

    public static DataManager instance()
    {
        return instance;
    }

    public synchronized ArrayList<NewsData> getNewsList(){
        return newsList;
    }

    @Subscribe
    public void updateArray(Articles data){
        getNewsList().clear();
        getNewsList().addAll(data.getArticles());

        for(NewsData newsData : data.getArticles()){
            if(catagoryMap.containsKey(newsData.getCategory())){
                catagoryMap.get(newsData.getCategory()).add(newsData);
            }
            else {
                LinkedList<NewsData> list = new LinkedList<NewsData>();
                list.add(newsData);
                catagoryMap.put(newsData.getCategory(), list);
            }
        }

        EventBusSingleton.instance().postEvent(new ViewUpdateEvent());
    }

}
