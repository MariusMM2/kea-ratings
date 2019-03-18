package uni.kea.marius.kearatings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import uni.kea.marius.kearatings.model.RepoItem;
import uni.kea.marius.kearatings.utils.AnimationUtils;
import uni.kea.marius.kearatings.utils.ModelBinding;

public class DetailActivity extends SingleFragmentActivity {
    private static final String TAG = "DetailActivity";

    private static final String EXTRA_ITEM_PARCEL = "item_parcel";

    private RepoItem mItem;
    private View mItemView;
    private ImageButton mImageButton;

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
    }

    @Override
    public void onBackPressed() {
        AnimationUtils.flip(mImageButton, false);
        super.onBackPressed();
    }
}
