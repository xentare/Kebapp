package com.example.juha.kebapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.android.volley.VolleyError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juha on 16/11/15.
 */
public class RestaurantActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private CommentArrayAdapter adapter;
    private ArrayList<Comment> comments;
    private DataHandler dataHandler;
    private Restaurant restaurant;

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
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBarRestaurantActivity);
        ratingBar.setRating(restaurant.stars);


        getData();

        ListView listView = (ListView)findViewById(R.id.commentsListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void getData(){
        if(dataHandler == null) {
            dataHandler = new DataHandler(this);
        }
        getOpenings();
        getComments();
    }

    private void getOpenings(){
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        dataHandler.requestRestaurantOpenings(restaurant.id, new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                TextView timestamp = (TextView) findViewById(R.id.openingHours);
                if (JSONParser.parseOpenings(result) != null) {
                    timestamp.append(JSONParser.parseOpenings(result));
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void getComments(){
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        dataHandler.requestComments(restaurant.id, new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                adapter.clear();
                adapter.addAll(JSONParser.parseComments(result));
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void postCommentButtonClicked(View view){
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        Map<String,String> params = new HashMap<>();
        final EditText editText = (EditText)findViewById(R.id.commentBox);
        params.put("restaurant_id",restaurant.id);
        params.put("text", editText.getText().toString());

        dataHandler.postComment(params, new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Log.d("POST", "Comment posted");
                editText.setText("");
                getComments();
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dataHandler.postCommentVote(comments.get(position).id, new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                getComments();
                Log.d("VOTE", "Upvote posted");
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }
}
