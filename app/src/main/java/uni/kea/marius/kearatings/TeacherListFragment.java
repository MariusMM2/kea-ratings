package uni.kea.marius.kearatings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import uni.kea.marius.kearatings.database.TeacherRepo;
import uni.kea.marius.kearatings.model.Teacher;

import java.util.List;

public class TeacherListFragment extends Fragment {
    @SuppressWarnings("unused")
    private static final String TAG = TeacherListFragment.class.getSimpleName();

    private RecyclerView mTeacherRecyclerView;
    private TeacherAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mTeacherRecyclerView = v.findViewById(R.id.recycler_view);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAdapter = new TeacherListFragment.TeacherAdapter();
        mTeacherRecyclerView.setAdapter(mAdapter);
    }

    private class TeacherHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private RatingBar mRatingBar;

        public TeacherHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            mNameTextView = itemView.findViewById(R.id.item_name);
            mRatingBar = itemView.findViewById(R.id.item_rating);
        }

        private void bind(Teacher teacher) {
            mNameTextView.setText(teacher.getName());
            mRatingBar.setRating(teacher.getRating());
        }
    }

    private class TeacherAdapter extends RecyclerView.Adapter<TeacherListFragment.TeacherHolder> {

        @NonNull
        @Override
        public TeacherListFragment.TeacherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TeacherListFragment.TeacherHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TeacherListFragment.TeacherHolder teacherHolder, int i) {
//            Teacher teacher = TeacherRepo.getInstance(getActivity()).read(i);
//            teacherHolder.bind(teacher);
        }

        @Override
        public void onBindViewHolder(@NonNull TeacherListFragment.TeacherHolder teacherHolder, int i, @NonNull List<Object> payloads) {
            Teacher teacher = TeacherRepo.getInstance(getActivity()).read(i);
            teacherHolder.bind(teacher);
        }

        @Override
        public int getItemCount() {
            return TeacherRepo.getInstance(getActivity()).size();
        }
    }
}
