package app;

import lombok.Builder;
import lombok.Value;

import java.util.function.Predicate;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
@Value
@Builder
public class User {
    int id;
    
    public boolean hasId(int id) {
        return this.id == id;
    }
    
    public static Predicate<User> withId(int id) {
        return user -> user.hasId(id);
    }
}
