package com.akhare.dailyhuntchallenge.Network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by akhare on 10/18/15.
 */
public class NewsFeedRequest extends VolleyRequest {

    private static String url = "https://dailyhunt.0x10.info/api/dailyhunt?type=json&query=list_news";
    public NewsFeedRequest(){
        super(Method.GET, url, Articles.class, null, null, null, new Response.Listener<Articles>() {
            @Override
            public void onResponse(Articles response) {

                EventBusSingleton.instance().postEvent(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBusSingleton.instance().postEvent(error);
            }
        });
    }
}
