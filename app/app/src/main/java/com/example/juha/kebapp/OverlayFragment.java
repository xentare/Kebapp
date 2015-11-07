package com.example.juha.kebapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Juha on 5.11.2015.
 */
public class OverlayFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getFragmentManager().beginTransaction().hide(this).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.overlay_fragment, container, false);
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
