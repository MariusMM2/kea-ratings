package uni.kea.marius.kearatings.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserTest {
    private static final String EMAIL_STRUCTURE = "@stud.kea.dk";

    @Test
    public void userNameFromEmail() {
        User user = new User(EMAIL_STRUCTURE, "mari16df@stud.kea.dk", "");
        String userName = user.getUserName();
        assertEquals("mari16df", userName);
    }

    @Test
    public void lowerCaseConversion() {
        User user = new User(EMAIL_STRUCTURE, "MaRI16DF@Stud.kea.DK", "");
        String email = user.getEmail();
        assertEquals("mari16df@stud.kea.dk", email);
    }

    @Test
    public void noUser() {
        User noUser = User.none();
        assertNull(noUser.getId());
        assertEquals("", noUser.getUserName());
        assertEquals("", noUser.getEmail());
    }
}