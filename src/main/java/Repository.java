import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class Repository {
    LegacyBackupRepository legacyBackupRepository = new LegacyBackupRepository();

    Option<User> find(int id) {
        return MockDatabase.users.find(User.withId(id));
    }

    Try<User> backupLookup(int id) {
        return Try.of(() -> legacyBackupRepository.backupFind(id));
    }

    Either<List<DatabaseUserFindReport>, User> findOrBackup(int id) {
        Option<User> normalUser = find(id);
        if (normalUser.isDefined()) {
            return Either.right(normalUser.get());
        }
        Try<User> backupUser = backupLookup(id);
        if (backupUser.isSuccess()) {
            return Either.right(backupUser.get());
        }
        return Either.left(List.of(new DatabaseUserFindReport(id, DatabaseType.NORMAL),
                new DatabaseUserFindReport(id, DatabaseType.BACKUP)));

    }

    @Value
    class DatabaseUserFindReport {
        int id;
        DatabaseType type;

        DatabaseUserFindReport(int id, DatabaseType type) {
            this.id = id;
            this.type = type;
        }
    }

    enum DatabaseType {
        NORMAL, BACKUP;
    }
}
