package uni.kea.marius.kearatings.database;

import android.content.Context;
import uni.kea.marius.kearatings.model.RepoItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class AbstractRepo<T extends RepoItem> implements Repo<T> {
    AbstractRepo(Context context) {
        items = new ArrayList<>();
    }

    private List<T> items;

    void addAll(Collection<T> newItems) {
        items.addAll(newItems);
    }

    public T[] readAll() {
        //noinspection unchecked,SuspiciousToArrayCall
        return (T[]) items.toArray(new RepoItem[]{});
    }

    public T read(int i) {
        return items.get(i);
    }

    @Override
    public int size() {
        return items.size();
    }
}
