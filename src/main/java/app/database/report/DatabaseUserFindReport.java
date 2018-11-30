package app.database.report;

import lombok.Value;

@Value
public class DatabaseUserFindReport {
    int id;
    DatabaseType type;

    private DatabaseUserFindReport(int id, DatabaseType type) {
        this.id = id;
        this.type = type;
    }
    
    public static DatabaseUserFindReport normal(int id) {
        return new DatabaseUserFindReport(id, DatabaseType.NORMAL);
    }

    public static DatabaseUserFindReport backup(int id) {
        return new DatabaseUserFindReport(id, DatabaseType.BACKUP);
    }
}