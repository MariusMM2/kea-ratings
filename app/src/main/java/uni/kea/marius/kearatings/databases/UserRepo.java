package uni.kea.marius.kearatings.databases;

import android.content.Context;
import uni.kea.marius.kearatings.R;
import uni.kea.marius.kearatings.models.User;

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
        final String[] users = context.getResources().getStringArray(R.array.users);
        final String[] ids = context.getResources().getStringArray(R.array.user_ids);
        String defaultPassword = context.getString(R.string.default_password);

        addAll(Arrays.asList(
                new User(ids[0], emailStructure, users[0], defaultPassword),
                new User(ids[1], emailStructure, users[1], defaultPassword),
                new User(ids[2], emailStructure, users[2], defaultPassword),
                new User(ids[3], emailStructure, users[3], defaultPassword),
                new User(ids[4], emailStructure, users[4], defaultPassword)));
    }


}
