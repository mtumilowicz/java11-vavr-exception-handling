package app;

import app.database.report.DatabaseUserFindReport;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;
import outer.EntityNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();

    @Test
    public void find_found() {
        Option<User> user = userRepository.find(2);

        assertTrue(user.isDefined());
        assertThat(user.get().getId(), is(2));
    }

    @Test
    public void find_notFound() {
        Option<User> user = userRepository.find(1);

        assertTrue(user.isEmpty());
    }

    @Test
    public void backupLookup_found() {
        Try<User> user = userRepository.backupLookup(1);

        assertTrue(user.isSuccess());
        assertThat(user.get().getId(), is(1));
    }

    @Test
    public void backupLookup_notFound() {
        Try<User> user = userRepository.backupLookup(2);

        assertTrue(user.isFailure());
        assertTrue(user.getCause() instanceof EntityNotFoundException);
    }

    @Test
    public void findOrBackup_found_our_database() {
        Either<List<DatabaseUserFindReport>, User> user = userRepository.findOrBackup(2);

        assertTrue(user.isRight());
        assertThat(user.get().getId(), is(2));
    }

    @Test
    public void findOrBackup_found_backup_database() {
        Either<List<DatabaseUserFindReport>, User> user = userRepository.findOrBackup(1);

        assertTrue(user.isRight());
        assertThat(user.get().getId(), is(1));
    }

    @Test
    public void findOrBackup_notFound() {
        Either<List<DatabaseUserFindReport>, User> user = userRepository.findOrBackup(3);

        assertTrue(user.isLeft());
        assertThat(user.getLeft(), is(List.of(
                DatabaseUserFindReport.normal(3),
                DatabaseUserFindReport.backup(3))));
    }
}