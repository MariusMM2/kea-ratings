package uni.kea.marius.kearatings.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import uni.kea.marius.kearatings.R;

public class AnimationUtils {
    public static void rotate(ImageButton imageButton, @AnimRes int animation) {

        Animation rotateAnim = android.view.animation.AnimationUtils.loadAnimation(imageButton.getContext(), animation);
        rotateAnim.setFillAfter(true);

        imageButton.startAnimation(rotateAnim);
    }

    public static void swapButtons(ImageView view1, ImageView view2, boolean stage) {
        int duration = view1.getResources().getInteger(android.R.integer.config_mediumAnimTime);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view1, View.ALPHA, stage ? 0f : 1f);
        fadeOut.setDuration(duration);
        fadeOut.setInterpolator(new LinearInterpolator());

        ObjectAnimator lower = ObjectAnimator.ofFloat(view1, "elevation", stage ?
                view1.getResources().getDimension(R.dimen.fab_elevation_low) :
                view1.getResources().getDimension(R.dimen.fab_elevation_high));
        lower.setDuration(duration);
        lower.setInterpolator(new LinearInterpolator());

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view2, View.ALPHA, stage ? 1f : 0f);
        fadeIn.setDuration(duration);
        fadeIn.setInterpolator(new LinearInterpolator());

        ObjectAnimator elevate = ObjectAnimator.ofFloat(view2, "elevation", stage ?
                view1.getResources().getDimension(R.dimen.fab_elevation_high) :
                view1.getResources().getDimension(R.dimen.fab_elevation_low));
        elevate.setDuration(duration);
        elevate.setInterpolator(new LinearInterpolator());

        ObjectAnimator rotate1 = ObjectAnimator.ofFloat(view1, View.ROTATION, stage ? 720 : 0);
        rotate1.setDuration(duration);
        rotate1.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator rotate2 = ObjectAnimator.ofFloat(view2, View.ROTATION, stage ? 720 : 0);
        rotate2.setDuration(duration);
        rotate2.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotate1).with(fadeOut).with(lower)
                .with(rotate2).with(fadeIn).with(elevate)
        ;
        animatorSet.start();
    }
}
