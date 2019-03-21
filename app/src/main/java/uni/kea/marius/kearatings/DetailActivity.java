package uni.kea.marius.kearatings;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageButton;
import uni.kea.marius.kearatings.model.RepoItem;
import uni.kea.marius.kearatings.util.AnimationUtils;
import uni.kea.marius.kearatings.util.ModelBinding;

public class DetailActivity extends SingleFragmentActivity {
    private static final String TAG = "DetailActivity";

    private static final String EXTRA_ITEM_PARCEL = "item_parcel";

    public static final String RESULT_ITEM_PARCEL = "result_item_parcel";

    private RepoItem mItem;
    private View mItemView;
    private ImageButton mImageButton;
    private CardView mFragmentCurtain;
    private DetailFragment mFragment;
    private FloatingActionButton mNewRatingFAB;
    private FloatingActionButton mSubmitRatingFAB;
    private boolean mAnimOn = false;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail;
    }

    static Intent newIntent(Context packageContext, RepoItem repoItem) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_ITEM_PARCEL, repoItem);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return DetailFragment.newInstance(mItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        mItem = getIntent().getParcelableExtra(EXTRA_ITEM_PARCEL);

        super.onCreate(savedInstanceState);

        mItemView = findViewById(R.id.include);
        ModelBinding.bindRepoItem(mItem, mItemView);

        mImageButton = findViewById(R.id.item_expand_button);
        mImageButton.setOnClickListener(v -> onBackPressed());
        AnimationUtils.rotate(mImageButton, R.anim.expand_to_details);

        mFragmentCurtain = findViewById(R.id.curtain);

        mNewRatingFAB = findViewById(R.id.new_rating);
        mNewRatingFAB.setOnClickListener(v -> animateCurtain(v, () -> {
            if (!mFragment.hasNewRating()) {
                mFragment.newRating();
            }
        }, () -> AnimationUtils.rotate(mImageButton, R.anim.expand_to_new_rating)));

        mSubmitRatingFAB = findViewById(R.id.submit_rating);
        mSubmitRatingFAB.setOnClickListener(v -> mFragment.submitRating());
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        mFragment = (DetailFragment) fragment;
    }

    @Override
    public void onBackPressed() {
        if (mFragment.hasNewRating()) {
            // return from the new rating section
            mItem = DetailFragment.getItem(mFragment);
            ModelBinding.bindRepoItem(mItem, mItemView);
            animateCurtain(mNewRatingFAB, () -> {
                mFragment.defaultRating();
                AnimationUtils.rotate(mImageButton, R.anim.expand_from_new_rating);
            });
            AnimationUtils.swapImage(mNewRatingFAB, mSubmitRatingFAB, false);
        } else {
            // return to MainActivity
            AnimationUtils.rotate(mImageButton, R.anim.expand_from_details);
            Intent returnIntent = new Intent();
            returnIntent.putExtra(RESULT_ITEM_PARCEL, mItem);
            setResult(Activity.RESULT_OK, returnIntent);
            super.onBackPressed();
        }
    }

    private void animateCurtain(View origin, Runnable afterHide) {
        animateCurtain(origin, afterHide, null);
    }

    private void animateCurtain(View origin, Runnable afterHide, Runnable afterReveal) {
        if (!mAnimOn) {
            mAnimOn = true;

            View rootView = findViewById(R.id.root_layout);

            final int duration = getResources().getInteger(android.R.integer.config_mediumAnimTime);

            int x = (int) (mFragmentCurtain.getWidth() - (rootView.getWidth() - (origin.getLeft() + origin.getWidth() / 2f)));
            int y = (int) (mFragmentCurtain.getHeight() - (rootView.getHeight() - (origin.getTop() + origin.getHeight() / 2f)));

            int startRadius = 0;
            int endRadius = (int) (Math.hypot(rootView.getWidth(), rootView.getHeight()));

            Animator anim = ViewAnimationUtils.createCircularReveal(mFragmentCurtain, x, y, startRadius, endRadius);

            AnimationUtils.swapImage(mNewRatingFAB, mSubmitRatingFAB, true);

            mFragmentCurtain.setVisibility(View.VISIBLE);
            anim.setDuration(duration);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (afterHide != null) {
                        afterHide.run();
                    }

                    Animator anim = ViewAnimationUtils.createCircularReveal(mFragmentCurtain, x, y, endRadius, startRadius);

                    anim.setDuration(duration);
                    anim.start();
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (afterReveal != null) {
                                afterReveal.run();
                            }

                            mFragmentCurtain.setVisibility(View.GONE);
                            mAnimOn = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    }
}
