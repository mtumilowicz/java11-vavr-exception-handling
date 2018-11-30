package app;

import app.database.MockDatabase;
import app.database.report.DatabaseUserFindReport;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import outer.LegacyBackupUserRepository;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class UserRepository {
    private final LegacyBackupUserRepository legacyBackupUserRepository = new LegacyBackupUserRepository();

    public Option<User> find(int id) {
        return MockDatabase.users.find(User.withId(id));
    }

    public Try<User> backupLookup(int id) {
        return Try.of(() -> legacyBackupUserRepository.find(id));
    }

    public Either<List<DatabaseUserFindReport>, User> findOrBackup(int id) {
        Option<User> normalUser = find(id);
        if (normalUser.isDefined()) {
            return Either.right(normalUser.get());
        }
        
        Try<User> backupUser = backupLookup(id);
        if (backupUser.isSuccess()) {
            return Either.right(backupUser.get());
        }
        
        return Either.left(List.of(
                DatabaseUserFindReport.normal(id),
                DatabaseUserFindReport.backup(id)
        ));

    }
}
