package uni.kea.marius.kearatings.util;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.model.RateableItem;

public class ModelBinding {
    public static void bindRepoItem(RateableItem rateableItem, View container) {
        TextView nameView = container.findViewById(R.id.item_name);
        RatingBar ratingBar = container.findViewById(R.id.item_rating);
        bindRepoItem(rateableItem, nameView, ratingBar, null, null);
    }

    public static void bindRepoItem(RateableItem rateableItem, TextView nameView, RatingBar ratingBar, ImageButton expandButton, View.OnClickListener buttonAction) {
        nameView.setText(rateableItem.getName());
        ratingBar.setRating(rateableItem.getRating());
        if (expandButton != null) expandButton.setOnClickListener(buttonAction);
    }
}
