package uni.kea.marius.kearatings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import uni.kea.marius.kearatings.model.RateableItem;
import uni.kea.marius.kearatings.model.Score;
import uni.kea.marius.kearatings.model.User;

import java.util.List;
import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
public class DetailFragment extends Fragment {
    private static final String TAG = "DetailFragment";

    // the fragment initialization parameter
    private static final String ARG_ITEM_PARCEL = "item_bundle";

    private static final String STATE_NEW_RATING = "new_rating";

    private User mCurrentUser;

    private RateableItem mItem;
    private Score mNewRating;
    private boolean mHasNewRating;

    private RecyclerView mRatingsRecyclerView;
    private RatingsAdapter mAdapter;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of
     * the fragment using the provided item.
     *
     * @param rateableItem The parcelled item.
     * @return A new instance of fragment DetailFragment.
     */
    static DetailFragment newInstance(RateableItem rateableItem) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM_PARCEL, rateableItem);
        fragment.setArguments(args);
        return fragment;
    }

    static RateableItem getItem(DetailFragment targetFragment) {
        return targetFragment.getArguments().getParcelable(ARG_ITEM_PARCEL);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setRetainInstance(true);
        if (getArguments() != null) {
            mItem = getArguments().getParcelable(ARG_ITEM_PARCEL);
            Log.d(TAG, "item: " + mItem.toString());
        } else {
            Log.e(TAG, "No arguments found");
            throw new IllegalStateException("Fragment arguments cannot be null");
        }

        mCurrentUser = UserLogin.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated()");

        if (savedInstanceState != null) {
            Log.d(TAG, "Loading state");
            //Restore the fragment's state here
            mNewRating = savedInstanceState.getParcelable(STATE_NEW_RATING);
            if (mNewRating != null) {
                Log.d(TAG, "new rating layout");
                mHasNewRating = true;
                prepareNewRatingLayout();
            } else {
                Log.d(TAG, "standard rating layout");
                mHasNewRating = false;
                prepareDefaultRatingLayout();
            }
        }

        mRatingsRecyclerView = view.findViewById(R.id.recycler_view);

        mAdapter = new RatingsAdapter();
        mRatingsRecyclerView.setAdapter(mAdapter);

        mAdapter.setScore(mHasNewRating ? mNewRating : mItem.getOverallScore());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        if (savedInstanceState != null) {
//            Log.d(TAG, "Loading state");
//            //Restore the fragment's state here
//            mNewRating = savedInstanceState.getParcelable(STATE_NEW_RATING);
//            if (mNewRating != null) {
//                mHasNewRating = true;
//                prepareNewRatingLayout();
//            } else {
//                Log.d(TAG, "standard rating layout");
//                mHasNewRating = false;
//            }
//        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
        outState.putParcelable(STATE_NEW_RATING, mNewRating);
        Log.d(TAG, "Saving state");
    }

    void newRating() {
        if (!mItem.isRatedBy(mCurrentUser)) {
            mNewRating = mItem.newScore(mCurrentUser);
        } else {
            mNewRating = mItem.getScore(mCurrentUser);
        }
        mHasNewRating = true;

        prepareNewRatingLayout();
    }

    void defaultRating() {
        mNewRating = null;
        mHasNewRating = false;
        prepareDefaultRatingLayout();
    }

    void submitRating() {
        if (mNewRating.isReadyToSubmit()) {
            mItem.addScore(mNewRating);
            getActivity().onBackPressed();
            Toast.makeText(getContext(), "Rating saved.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Please rate all criterias.", Toast.LENGTH_SHORT).show();
        }
    }

    boolean hasNewRating() {
        return mHasNewRating;
    }

    private void prepareNewRatingLayout() {
        Log.d(TAG, "Preparing new rating layout");
        getActivity().findViewById(R.id.new_rating_title).setVisibility(View.VISIBLE);
        mAdapter.setScore(mNewRating);
        mAdapter.notifyDataSetChanged();
    }

    private void prepareDefaultRatingLayout() {
        Log.d(TAG, "Preparting default rating layout");
        getActivity().findViewById(R.id.new_rating_title).setVisibility(View.GONE);
        mAdapter.setScore(mItem.getOverallScore());
        mAdapter.notifyDataSetChanged();
    }


    private class RatingHolder extends RecyclerView.ViewHolder {
        private Map.Entry<String, Float> mRating;
        private TextView mNameView;
        private RatingBar mRatingBar;


        public RatingHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.list_item_rating_criteria, parent, false));
            Log.d(TAG, "Holder created");
            mNameView = itemView.findViewById(R.id.criteria_name);
            mRatingBar = itemView.findViewById(R.id.criteria_rating);

        }

        public void bind(Map.Entry<String, Float> ratingEntry) {
            mRating = ratingEntry;

            mNameView.setText(mRating.getKey());
            mRatingBar.setRating(mRating.getValue());
            mRatingBar.setIsIndicator(!mHasNewRating);
            if (mHasNewRating) {
                mRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                    if (fromUser) {
                        float boundRating = Math.max(rating, Score.MIN);
                        Log.d(TAG, "PRE_OREO val:" + boundRating);
                        mRating.setValue(boundRating);
                        ratingBar.setRating(boundRating);
                    }
                });
            }
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
