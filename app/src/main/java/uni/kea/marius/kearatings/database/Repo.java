package uni.kea.marius.kearatings.database;

public interface Repo<T> {
    T[] readAll();

    T read(int i);

    int size();
}
