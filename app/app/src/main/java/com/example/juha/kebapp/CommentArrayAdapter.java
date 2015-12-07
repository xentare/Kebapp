package com.example.juha.kebapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juha on 19.11.2015.
 */
class CommentArrayAdapter extends ArrayAdapter<Comment> {


    public CommentArrayAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_layout,parent,false);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.commentTextView);
        textView.setText(comment.text);

        TextView upvotes = (TextView)convertView.findViewById(R.id.upvotesTextView);
        upvotes.setText(comment.upVotes);

        return convertView;
    }
}
