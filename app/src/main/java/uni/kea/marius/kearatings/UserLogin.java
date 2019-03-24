package uni.kea.marius.kearatings;

import android.content.Context;
import android.util.Log;
import uni.kea.marius.kearatings.databases.Repos;
import uni.kea.marius.kearatings.models.RepoItem;
import uni.kea.marius.kearatings.models.User;

import java.util.Arrays;

public class UserLogin {
    private static final String TAG = "UserLogin";
    private static User sLoggedInUser;

    public static boolean login(Context context, String email, String password) {
        RepoItem[] usersArray = Repos.get(Repos.USER_REPO, context).readAll();
        User[] users = Arrays.copyOf(usersArray, usersArray.length, User[].class);
        Arrays.sort(users, (o1, o2) -> o1.getEmail().compareTo(o2.getEmail()));
        Log.i(TAG, "grabbed users from repo: " + Arrays.toString(users));
        User pendingValidation = new User(context.getString(R.string.email_structure), email, password);
        Log.i(TAG, "looking for " + pendingValidation.toString());
        int userIndex = Arrays.binarySearch(users, pendingValidation, (o1, o2) -> {
            if (o1.getEmail().equals(o2.getEmail()) &&
                    o1.getPassword().equals(o2.getPassword())) {
                return 0;
            }
            return -1;
        });

        if (userIndex < 0) {
            Log.w(TAG, String.format("User '%s' not found", pendingValidation));
            return false;
        } else {
            Log.i(TAG, String.format("User '%s' found, logging in", pendingValidation));
            sLoggedInUser = users[userIndex];
            return true;
        }
    }

    public static void logout() {
        Log.i(TAG, "Logging out");
        sLoggedInUser = null;
    }

    public static User getUser() {
        return sLoggedInUser;
    }
}
