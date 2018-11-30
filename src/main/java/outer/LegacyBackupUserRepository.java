package outer;

import app.User;
import outer.database.MockBackupDatabase;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class LegacyBackupUserRepository {
    public User find(int id) {
        return MockBackupDatabase.users.find(User.withId(id))
                .getOrElseThrow(EntityNotFoundException::new);
    }
}
