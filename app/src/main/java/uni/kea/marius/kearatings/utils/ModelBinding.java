package uni.kea.marius.kearatings.utils;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.model.RepoItem;

public class ModelBinding {
    public static void bindRepoItem(RepoItem repoItem, View container) {
        TextView nameView = container.findViewById(R.id.item_name);
        RatingBar ratingBar = container.findViewById(R.id.item_rating);
        bindRepoItem(repoItem, nameView, ratingBar, null, null);
    }

    public static void bindRepoItem(RepoItem repoItem, TextView nameView, RatingBar ratingBar, ImageButton expandButton, View.OnClickListener buttonAction) {
        nameView.setText(repoItem.getName());
        ratingBar.setRating(repoItem.getRating());
        if (expandButton != null) expandButton.setOnClickListener(buttonAction);
    }
}
