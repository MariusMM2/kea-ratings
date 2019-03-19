package uni.kea.marius.kearatings.database;

import uni.kea.marius.kearatings.model.RepoItem;

public interface Repo {
    RepoItem[] readAll();

    RepoItem read(int i);

    int size();
}
