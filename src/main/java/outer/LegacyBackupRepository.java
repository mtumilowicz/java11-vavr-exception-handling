package outer;

import app.User;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class LegacyBackupRepository {
    User backupFind(int id) {
        return MockBackupDatabase.users.find(User.withId(id))
                .getOrElseThrow(EntityNotFoundException::new);
    }
}
