package uni.kea.marius.kearatings.utils;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.models.RateableItem;

/**
 * Utility class for binding a model to its presentable view
 */
public class ModelBinding {
    // Binds an item to a container
    public static void bindRepoItem(RateableItem rateableItem, View container) {
        TextView nameView = container.findViewById(R.id.item_name);
        RatingBar ratingBar = container.findViewById(R.id.item_rating);
        TextView reviewCount = container.findViewById(R.id.review_count);
        bindRepoItem(rateableItem, nameView, ratingBar, reviewCount, null, null);
    }

    // Binds an item to specific elements of a container,
    // Used for the ViewHolder pattern
    public static void bindRepoItem(RateableItem rateableItem, TextView nameView, RatingBar ratingBar, TextView reviewCount, ImageButton expandButton, View.OnClickListener buttonAction) {
        nameView.setText(rateableItem.getName());
        ratingBar.setRating(rateableItem.getRating());
        int scoreCount = rateableItem.getScoreCount();
        reviewCount.setText(reviewCount.getResources().getQuantityString(R.plurals.review_count, scoreCount, scoreCount));

        if (expandButton != null) expandButton.setOnClickListener(buttonAction);
    }
}
