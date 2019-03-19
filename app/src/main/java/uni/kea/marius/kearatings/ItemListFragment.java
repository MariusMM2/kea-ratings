package uni.kea.marius.kearatings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import uni.kea.marius.kearatings.database.Repo;
import uni.kea.marius.kearatings.model.RepoItem;
import uni.kea.marius.kearatings.util.ModelBinding;

import java.util.List;

public class ItemListFragment extends Fragment {

    private Repo mRepo;
    private RecyclerView mRecyclerView;
    private CourseAdapter mAdapter;

    public ItemListFragment() {

    }

    public void setRepo(Repo repo) {
        mRepo = repo;
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

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RepoItem mItem;
        private TextView mNameTextView;
        private RatingBar mRatingBar;
        private ImageButton mExpandButton;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_preview, parent, false));
            mNameTextView = itemView.findViewById(R.id.item_name);
            mRatingBar = itemView.findViewById(R.id.item_rating);
            mExpandButton = itemView.findViewById(R.id.item_expand_button);
        }

        private void bind(RepoItem item) {
            mItem = item;
            ModelBinding.bindRepoItem(item, mNameTextView, mRatingBar, mExpandButton, this);
        }

        @Override
        public void onClick(View v) {
            Intent i = DetailActivity.newIntent(getContext(), mItem);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    itemView.findViewById(R.id.item_preview), "item");
            startActivity(i, options.toBundle());
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
            RepoItem item = mRepo.read(i);
            itemHolder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mRepo.size();
        }
    }
}
