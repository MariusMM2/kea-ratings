package uni.kea.marius.kearatings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class DetailActivity extends SingleFragmentActivity implements DetailFragment.OnFragmentInteractionListener {

    private static final String EXTRA_ITEM_BUNDLE = "uni.kea.marius.kearatings.DetailActivity.contents_parcel";

    static Intent newIntent(Context packageContext, Bundle itemBundle) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_ITEM_BUNDLE, itemBundle);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return DetailFragment.newInstance(getIntent().getBundleExtra(EXTRA_ITEM_BUNDLE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
