package uni.kea.marius.kearatings.database;

import android.content.Context;
import uni.kea.marius.kearatings.model.RepoItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class AbstractRepo implements Repo {
    private List<RepoItem> items;

    AbstractRepo(Context context) {
        items = new ArrayList<>();
    }

    @Override
    public RepoItem[] readAll() {
        return items.toArray(new RepoItem[]{});
    }

    public RepoItem read(int i) {
        return items.get(i);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public void update(int index, RepoItem item) {
        items.set(index, item);
    }

    void addAll(Collection<RepoItem> newItems) {
        items.addAll(newItems);
    }
}
