package app.database;

import app.User;
import io.vavr.collection.List;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class MockDatabase {
    public static List<User> users = List.of(User.builder().id(2).build());
}
