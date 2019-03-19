package uni.kea.marius.kearatings;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import uni.kea.marius.kearatings.model.RepoItem;
import uni.kea.marius.kearatings.util.AnimationUtils;
import uni.kea.marius.kearatings.util.ModelBinding;

public class DetailActivity extends SingleFragmentActivity {
    private static final String TAG = "DetailActivity";

    private static final String EXTRA_ITEM_PARCEL = "item_parcel";

    private RepoItem mItem;
    private View mItemView;
    private ImageButton mImageButton;
    private FrameLayout mFragmentLayout;
    private ImageView mFragmentBackground;

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

        mFragmentLayout = findViewById(R.id.fragment_container);
        mFragmentBackground = findViewById(R.id.background);
    }

    @Override
    public void onBackPressed() {
        AnimationUtils.flip(mImageButton, false);
        super.onBackPressed();
    }

    public void addRating(View view) {
        int x = (int) (view.getLeft() + view.getWidth() / 2f);
        int y = (int) (view.getTop() + view.getHeight() / 2f);

        int startRadius = 0;
        View rootView = findViewById(R.id.root_layout);
        int endRadius = (int) (Math.hypot(rootView.getWidth(), rootView.getHeight()));

        Animator anim = ViewAnimationUtils.createCircularReveal(mFragmentBackground, x, y, startRadius, endRadius);

        mFragmentBackground.setVisibility(View.VISIBLE);
        anim.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
        anim.start();
    }
}
