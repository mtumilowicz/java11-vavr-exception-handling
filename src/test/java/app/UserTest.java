package app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class UserTest {

    @Test
    public void hasId_true() {
        assertTrue(User.builder().id(1).build().hasId(1));
    }

    @Test
    public void hasId_false() {
        assertFalse(User.builder().id(1).build().hasId(2));
    }
}