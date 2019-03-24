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
import uni.kea.marius.kearatings.database.Repo;
import uni.kea.marius.kearatings.database.Repos;
import uni.kea.marius.kearatings.model.RateableItem;
import uni.kea.marius.kearatings.util.ModelBinding;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class ItemListFragment extends Fragment {
    private static final String TAG = "ItemListFragment";

    private static final String ARG_ITEM_TYPE = "item_type";

    private Repo mRepo;
    private RecyclerView mRecyclerView;
    private CourseAdapter mAdapter;
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
            int itemType = getArguments().getInt(ARG_ITEM_TYPE);
            Log.d(TAG, "itemType: " + itemType);
            mRepo = Repos.get(itemType, getContext());
        } else {
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

        mAdapter = new CourseAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

    private class CourseAdapter extends RecyclerView.Adapter<ItemHolder> {

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
