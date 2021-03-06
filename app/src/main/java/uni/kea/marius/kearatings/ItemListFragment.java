package uni.kea.marius.kearatings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import uni.kea.marius.kearatings.databases.Repo;
import uni.kea.marius.kearatings.databases.Repos;
import uni.kea.marius.kearatings.models.RateableItem;
import uni.kea.marius.kearatings.utils.ModelBinding;

import java.util.List;

/**
 * Fragment containing a list of items (either courses or teachers)
 */
@SuppressWarnings("FieldCanBeLocal")
public class ItemListFragment extends Fragment {
    private static final String TAG = "ItemListFragment";

    // The item_type argument is used to differentiate between the two
    // repositories available, using the Repos class
    private static final String ARG_ITEM_TYPE = "item_type";

    private Repo mRepo;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    // Holds a reference to the item selected from the list,
    // to know which one to update when returning from DetailActivity
    private int mSelectedItem;

    static ItemListFragment newInstance(int itemType) {
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_TYPE, itemType);
        ItemListFragment fragment = new ItemListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ItemListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Grab item_type from arguments and use it
            // to reference the specific repository
            int itemType = getArguments().getInt(ARG_ITEM_TYPE);
            Log.d(TAG, "itemType: " + itemType);
            mRepo = Repos.get(itemType, getContext());
        } else {
            // If no arguments were found, force close
            Log.e(TAG, "No arguments found");
            throw new IllegalStateException("Fragment arguments cannot be null");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mAdapter = new ItemAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // This request code is set by DetailActivity,
        // whose data is the modified Item
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                RateableItem item = data.getParcelableExtra(DetailActivity.RESULT_ITEM_PARCEL);
                mRepo.update(mSelectedItem, item);
                mAdapter.notifyItemChanged(mSelectedItem);
            } else {
                if (data != null) {
                    Log.e(TAG, "DetailActivity returned negative, " + data.toString());
                } else {
                    Log.e(TAG, "DetailActivity returned nothing");
                }

            }
        }
    }

    /**
     * ViewHolder for a RateableItem.
     * Holds the title, the rating, number of ratings
     * and an action for the expand button.
     */
    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RateableItem mItem;
        private TextView mNameTextView;
        private RatingBar mRatingBar;
        private TextView mReviewCount;
        private ImageButton mExpandButton;

        ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_preview, parent, false));
            mNameTextView = itemView.findViewById(R.id.item_name);
            mRatingBar = itemView.findViewById(R.id.item_rating);
            mExpandButton = itemView.findViewById(R.id.item_expand_button);
            mReviewCount = itemView.findViewById(R.id.review_count);
        }

        private void bind(RateableItem item) {
            mItem = item;
            ModelBinding.bindRepoItem(item, mNameTextView, mRatingBar, mReviewCount, mExpandButton, this);
        }

        @Override
        public void onClick(View v) {
            mSelectedItem = getAdapterPosition();

            Intent i = DetailActivity.newIntent(getContext(), mItem);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    itemView.findViewById(R.id.item_preview), "item");
            startActivityForResult(i, 1, options.toBundle());
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {

        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i, @NonNull List<Object> payloads) {
            RateableItem item = (RateableItem) mRepo.read(i);
            itemHolder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mRepo.size();
        }
    }
}
