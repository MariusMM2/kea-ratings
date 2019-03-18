package uni.kea.marius.kearatings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import uni.kea.marius.kearatings.model.RepoItem;
import uni.kea.marius.kearatings.model.Score;

import java.util.List;
import java.util.Map;

public class DetailFragment extends Fragment {
    private static final String TAG = "DetailFragment";

    // the fragment initialization parameter
    private static final String ARG_ITEM_PARCEL = "item_bundle";

    private RepoItem mItem;

    private RecyclerView mRatingsRecyclerView;
    private RatingsAdapter mAdapter;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of
     * the fragment using the provided item.
     *
     * @param repoItem The parcelled item.
     * @return A new instance of fragment DetailFragment.
     */
    public static DetailFragment newInstance(RepoItem repoItem) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM_PARCEL, repoItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = getArguments().getParcelable(ARG_ITEM_PARCEL);
            Log.d(TAG, "item: " + mItem.toString());
        } else {
            Log.e(TAG, "No arguments found");
            throw new IllegalStateException("Fragment arguments cannot be null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRatingsRecyclerView = view.findViewById(R.id.ratings_recycler_view);
        mRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new RatingsAdapter();
        mRatingsRecyclerView.setAdapter(mAdapter);

        mAdapter.setScore(mItem.getOverallScore());
        mAdapter.notifyDataSetChanged();
    }

    private class RatingHolder extends RecyclerView.ViewHolder {
        private Map.Entry<String, Float> mRating;
        private TextView mNameView;
        private RatingBar mRatingBar;


        public RatingHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.list_item_rating_criteria, parent, false));
            mNameView = itemView.findViewById(R.id.criteria_name);
            mRatingBar = itemView.findViewById(R.id.criteria_rating);
        }

        public void bind(Map.Entry<String, Float> ratingEntry) {
            mRating = ratingEntry;

            mNameView.setText(mRating.getKey());
            mRatingBar.setRating(mRating.getValue());
        }
    }

    private class RatingsAdapter extends RecyclerView.Adapter<RatingHolder> {
        private Score mScore;

        @NonNull
        @Override
        public RatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RatingHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RatingHolder ratingHolder, int i) {

        }

        @Override
        public void onBindViewHolder(@NonNull RatingHolder holder, int position, @NonNull List<Object> payloads) {
            holder.bind(mScore.get(position));
        }

        @Override
        public int getItemCount() {
            return mScore.getSize();
        }

        public void setScore(Score score) {
            mScore = score;
        }
    }
}
