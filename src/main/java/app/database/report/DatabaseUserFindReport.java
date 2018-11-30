package app.database.report;

import lombok.Value;

@Value
public class DatabaseUserFindReport {
    int userId;
    DatabaseType type;

    private DatabaseUserFindReport(int userId, DatabaseType type) {
        this.userId = userId;
        this.type = type;
    }
    
    public static DatabaseUserFindReport normal(int id) {
        return new DatabaseUserFindReport(id, DatabaseType.NORMAL);
    }

    public static DatabaseUserFindReport backup(int id) {
        return new DatabaseUserFindReport(id, DatabaseType.BACKUP);
    }
}