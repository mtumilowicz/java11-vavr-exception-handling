package outer.database;

import app.User;
import io.vavr.collection.List;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class MockBackupDatabase {
    public static List<User> users = List.of(User.builder().id(1).build());
}
