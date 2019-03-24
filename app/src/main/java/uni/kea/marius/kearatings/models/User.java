package uni.kea.marius.kearatings.models;

import java.util.UUID;

public class User implements RepoItem {
    private UUID mId;
    private String mUserName;
    private String mEmail;
    private String mPassword;

    private User() {
    }

    public User(String emailStructure, String email, String password) {
        this(UUID.randomUUID().toString(), emailStructure, email, password);
    }

    public User(String uuidString, String emailStructure, String email, String password) {
        mId = UUID.fromString(uuidString);
        email = email.toLowerCase();
        mUserName = email.substring(0, email.indexOf(emailStructure));
        mEmail = email;
        mPassword = password;
    }

    public UUID getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public static User none() {
        User noUser = new User();
        noUser.mId = null;
        noUser.mEmail = "";
        noUser.mUserName = "";
        return noUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
