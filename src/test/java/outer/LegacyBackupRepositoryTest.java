package outer;

import app.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class LegacyBackupRepositoryTest {

    private LegacyBackupRepository repository = new LegacyBackupRepository();

    @Test
    public void find_found() {
        User user = repository.find(1);
        
        assertThat(user.getId(), is(1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void find_notFound() {
        repository.find(2);
    }
}