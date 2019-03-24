package uni.kea.marius.kearatings.databases;

import uni.kea.marius.kearatings.models.RepoItem;

public interface Repo {
    RepoItem[] readAll();

    RepoItem read(int i);

    int size();

    void update(int index, RepoItem item);
}
