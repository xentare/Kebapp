package com.example.juha.kebapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.media.Rating;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Juha on 5.11.2015.
 */
public class OverlayFragment extends Fragment {

    DataHandler dataHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dataHandler = new DataHandler(getActivity());
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overlay_fragment, container, false);

        if(getArguments().containsKey("restaurant")){
            Restaurant restaurant = getArguments().getParcelable("restaurant");
            TextView textView = (TextView)view.findViewById(R.id.name);
            textView.setText(restaurant.name);
            RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
            ratingBar.setRating(restaurant.stars);
        }
        return view;
    }

    /**
    * Method to slide fragment up/down
    * http://trickyandroid.com/fragments-translate-animation/
     */
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float displayHeight = size.y;

        Animator animator = null;
        if(enter) {
            animator = ObjectAnimator.ofFloat(this, "translationY", displayHeight, 0);
        }
        else {
            animator = ObjectAnimator.ofFloat(this, "translationY", 0, displayHeight);
        }

        animator.setDuration(300);
        return animator;
    }
}
