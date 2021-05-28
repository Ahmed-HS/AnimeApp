package com.team.animeapp.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class AnimationHelper {

    public static void playAnimation(View view,int animId)
    {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(),animId);
        long duration = 500;
        Interpolator interpolator = new FastOutSlowInInterpolator();
        long startOffset = 0;

        animation.setDuration(duration);
        animation.setStartOffset(startOffset);
        animation.setInterpolator(interpolator);

        view.startAnimation(animation);
    }
}
