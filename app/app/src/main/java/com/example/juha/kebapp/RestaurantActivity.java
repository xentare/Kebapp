package com.example.juha.kebapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juha on 16/11/15.
 */
public class RestaurantActivity extends Activity {

    CommentArrayAdapter adapter;
    ArrayList<String> comments;
    DataHandler dataHandler;
    Restaurant restaurant;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comments = new ArrayList<>();
        adapter = new CommentArrayAdapter(getApplicationContext(),comments);

        setContentView(R.layout.restaurant_activity);
        Bundle extras = getIntent().getExtras();
        restaurant = extras.getParcelable("restaurant");
        TextView textView = (TextView)findViewById(R.id.restaurantName);
        textView.setText(restaurant.name);

        getData();

        ListView listView = (ListView)findViewById(R.id.commentsListView);
        listView.setAdapter(adapter);
    }

    public void getData(){
        if(dataHandler == null) {
            dataHandler = new DataHandler(this);
        }
        dataHandler.requestComments(restaurant.id,new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                adapter.addAll(JSONParser.parseComments(result));
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    /*
    *TODO: Make cleaver update of Comments when posted
     */
    public void postCommentButtonClicked(View view){
        Map<String,String> params = new HashMap<>();
        EditText editText = (EditText)findViewById(R.id.commentBox);
        params.put("restaurant_id",restaurant.id);
        params.put("text",editText.getText().toString());

        dataHandler.postComment(params, new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("POST","Comment posted");
                //getData();
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

}
