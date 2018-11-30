package app;

import app.database.MockDatabase;
import app.database.report.DatabaseUserFindReport;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import outer.LegacyBackupRepository;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class Repository {
    private final LegacyBackupRepository legacyBackupRepository = new LegacyBackupRepository();

    public Option<User> find(int id) {
        return MockDatabase.users.find(User.withId(id));
    }

    public Try<User> backupLookup(int id) {
        return Try.of(() -> legacyBackupRepository.find(id));
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
