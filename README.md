[![Build Status](https://travis-ci.com/mtumilowicz/java11-vavr-exception-handling.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-vavr-exception-handling)

# java11-vavr-exception-handling
Simple project to show how to handle exceptions using vavr.

# preface
Please refer my other github projects:
* https://github.com/mtumilowicz/java11-vavr-option
* https://github.com/mtumilowicz/java11-vavr-try
* https://github.com/mtumilowicz/java11-vavr-either

# project description
1. Suppose we have outer repository (to backup database) 
that we cannot modify. What is more - `find` method throws 
`EntityNotFoundException`:
    ```
    public class LegacyBackupUserRepository {
        User find(int id) {
            return MockBackupDatabase.users.find(User.withId(id))
                    .getOrElseThrow(EntityNotFoundException::new);
        }
    }
    ```
1. in our application we have also a repository that:
    * has `find` method that will lookup into our database
    * has `backupLookup` method that will call `find` method
    from repository mentioned above (`LegacyBackupUserRepository`)
    * has `findOrBackup` method that will try to lookup in
    our database and:
        * if user is found returns it
        * if user is not found lookup in backup database
1. we want to model our `UserRepository` in a right way, so:
    * `Option` is perfect for modelling existence:
        ```
        Option<User> find(int id) {
            return MockDatabase.users.find(User.withId(id));
        }
        ```
    * `Try` is perfect to cope with third-party libraries
    that throws exceptions
        ```
        Try<User> backupLookup(int id) {
            return Try.of(() -> legacyBackupUserRepository.find(id));
        }
        ```
    * `Either` is perfect as a tailored error container
        ```
        Either<List<DatabaseUserFindReport>, User> findOrBackup(int id) {
            Option<User> normalUser = find(id);
            if (normalUser.isDefined()) {
                return Either.right(normalUser.get());
            }
            
            Try<User> backupUser = backupLookup(id);
            if (backupUser.isSuccess()) {
                return Either.right(backupUser.get());
            }
            
            return Either.left(List.of(
                    new DatabaseUserFindReport(id, DatabaseType.NORMAL),
                    new DatabaseUserFindReport(id, DatabaseType.BACKUP)
            ));
        
        }
        ```
        **summary**: we return user if found otherwise list
        databases that we looked-up into.
# tests
We provide tests for mentioned above functionality.