package uni.kea.marius.kearatings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import uni.kea.marius.kearatings.database.CourseRepo;
import uni.kea.marius.kearatings.model.Course;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseListFragment extends Fragment {
    @SuppressWarnings("unused")
    private static final String TAG = CourseListFragment.class.getSimpleName();

    private RecyclerView mCourseRecyclerView;
    private CourseAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mCourseRecyclerView = v.findViewById(R.id.recycler_view);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCourseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CourseAdapter();
        mCourseRecyclerView.setAdapter(mAdapter);
    }

    private class CourseHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;

        public CourseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_course, parent, false));
            mNameTextView = itemView.findViewById(R.id.course_name);
        }

        private void bind(Course course) {
            mNameTextView.setText(course.getName());
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
//            Course course = CourseRepo.getInstance(getActivity()).read(i);
//            courseHolder.bind(course);
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
