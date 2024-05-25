package com.example.project_app_book.model;


import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.project_app_book.R;

public class AnimationUtil {

    public interface AnimationListener {
        void onAnimationEnd();
    }
    public static void applyScaleAnimation_fade(Context context, View view) {
        Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

        view.startAnimation(fadeIn);
    }
    public static void applyScaleAnimation(Context context, View view) {
        Animation scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);

        view.startAnimation(scaleDown);
        scaleDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(scaleUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    public static void applyScaleAnimation(Context context, View view, Runnable onAnimationEnd) {
        Animation scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);

        view.startAnimation(scaleDown);
        scaleDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(scaleUp);
                scaleUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (onAnimationEnd != null) {
                            onAnimationEnd.run();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }


        });
    }
    public static void applyScaleAnimation(Context context, View view, final AnimationListener listener) {
        // Define your scale animation here
        Animation scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);

        // Start the animation
        view.startAnimation(scaleDown);

        // Set up animation listener to handle animation end
        scaleDown.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
                // Not needed in this case
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                // Call onAnimationEnd() of the custom AnimationListener
                if (listener != null) {
                    listener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {
                // Not needed in this case
            }
        });
    }
}
