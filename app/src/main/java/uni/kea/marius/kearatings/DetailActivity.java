package uni.kea.marius.kearatings;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    private RepoItem mItem;
    private View mItemView;
    private ImageButton mImageButton;
    private CardView mFragmentBackground;
    private DetailFragment mFragment;
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
        AnimationUtils.flip(mImageButton, true);

        mFragmentBackground = findViewById(R.id.background);

//        mFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        mFragment = (DetailFragment) fragment;
    }

    @Override
    public void onBackPressed() {
        AnimationUtils.flip(mImageButton, false);
        super.onBackPressed();
    }

    public void addRating(View view) {
        if (!mAnimOn) {
            mAnimOn = true;

            View rootView = findViewById(R.id.root_layout);

            int x = (int) (mFragmentBackground.getWidth() - (rootView.getWidth() - (view.getLeft() + view.getWidth() / 2f)));
            int y = (int) (mFragmentBackground.getHeight() - (rootView.getHeight() - (view.getTop() + view.getHeight() / 2f)));

            int startRadius = 0;
            int endRadius = (int) (Math.hypot(rootView.getWidth(), rootView.getHeight()));

            Animator anim = ViewAnimationUtils.createCircularReveal(mFragmentBackground, x, y, startRadius, endRadius);

            mFragmentBackground.setVisibility(View.VISIBLE);
            anim.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mFragment.newRating();

                    Animator anim = ViewAnimationUtils.createCircularReveal(mFragmentBackground, x, y, endRadius, startRadius);

                    anim.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
                    anim.start();
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mFragmentBackground.setVisibility(View.GONE);
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
