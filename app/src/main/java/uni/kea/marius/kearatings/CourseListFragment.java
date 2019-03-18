package uni.kea.marius.kearatings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import uni.kea.marius.kearatings.database.CourseRepo;
import uni.kea.marius.kearatings.model.Course;
import uni.kea.marius.kearatings.model.RepoItem;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseListFragment extends Fragment {
    @SuppressWarnings("unused")
    private static final String TAG = CourseListFragment.class.getSimpleName();

    private RecyclerView mCourseRecyclerView;
    private CourseAdapter mAdapter;
    private Callback mCallback;

    public interface Callback {
        void onItemSelected(Bundle itemBundle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mCourseRecyclerView = v.findViewById(R.id.recycler_view);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAdapter = new CourseAdapter();
        mCourseRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            mCallback = (Callback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private RatingBar mRatingBar;
        private ImageButton mExpandButton;
        private RepoItem mItem;

        public CourseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_preview, parent, false));
            mNameTextView = itemView.findViewById(R.id.item_name);
            mRatingBar = itemView.findViewById(R.id.item_rating);
            mExpandButton = itemView.findViewById(R.id.item_expand_button);
        }

        private void bind(Course course) {
            mItem = course;
            mNameTextView.setText(course.getName());
            mRatingBar.setRating(course.getRating());
            mExpandButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(DetailFragment.KEY_ITEM_PARCEL, mItem);
            Log.d(TAG, "parcel: " + bundle.toString());
            mCallback.onItemSelected(bundle);
        }
    }

    private class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {

        @NonNull
        @Override
        public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CourseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseHolder courseHolder, int i) {

        }

        @Override
        public void onBindViewHolder(@NonNull CourseHolder courseHolder, int i, @NonNull List<Object> payloads) {
            Course course = CourseRepo.getInstance(getActivity()).read(i);
            courseHolder.bind(course);
        }

        @Override
        public int getItemCount() {
            return CourseRepo.getInstance(getActivity()).size();
        }
    }
}
