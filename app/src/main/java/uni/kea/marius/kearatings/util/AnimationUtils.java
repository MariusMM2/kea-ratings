package uni.kea.marius.kearatings.util;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

public class AnimationUtils {
    public static void flip(ImageButton imageButton, boolean toHalfPI) {
        RotateAnimation rotateAnimation = toHalfPI ? new RotateAnimation(0, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f) : new RotateAnimation(180f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        rotateAnimation.setDuration(150);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        imageButton.startAnimation(rotateAnimation);
    }
}
