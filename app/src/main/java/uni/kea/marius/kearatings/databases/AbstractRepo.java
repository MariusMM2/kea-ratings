package uni.kea.marius.kearatings.databases;

import android.content.Context;
import uni.kea.marius.kearatings.models.RepoItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class AbstractRepo implements Repo {
    private List<RepoItem> mItems;

    AbstractRepo(Context context) {
        mItems = new ArrayList<>();
    }

    @Override
    public RepoItem[] readAll() {
        return mItems.toArray(new RepoItem[]{});
    }

    public RepoItem read(int i) {
        return mItems.get(i);
    }

    @Override
    public int size() {
        return mItems.size();
    }

    @Override
    public void update(int index, RepoItem item) {
        mItems.set(index, item);
    }

    void addAll(Collection<RepoItem> newItems) {
        mItems.addAll(newItems);
    }
}
