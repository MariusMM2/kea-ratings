package uni.kea.marius.kearatings.database;

import android.content.Context;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.model.User;

import java.util.Arrays;

class UserRepo extends AbstractRepo {
    private static UserRepo sInstance;

    static UserRepo getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UserRepo(context);
        }
        return sInstance;
    }

    private UserRepo(Context context) {
        super(context);
        String emailStructure = context.getString(R.string.email_structure);

        addAll(Arrays.asList(
                new User(emailStructure, "mari12df@stud.kea.dk", "12345"),
                new User(emailStructure, "mari13df@stud.kea.dk", "12345"),
                new User(emailStructure, "mari14df@stud.kea.dk", "12345"),
                new User(emailStructure, "mari15df@stud.kea.dk", "12345"),
                new User(emailStructure, "mari16df@stud.kea.dk", "12345")));
    }


}
