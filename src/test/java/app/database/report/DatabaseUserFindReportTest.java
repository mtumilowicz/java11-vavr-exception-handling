package app.database.report;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
public class DatabaseUserFindReportTest {

    @Test
    public void normal() {
        DatabaseUserFindReport report = DatabaseUserFindReport.normal(1);
        
        assertThat(report.getId(), is(1));
        assertThat(report.getType(), is(DatabaseType.NORMAL));
    }

    @Test
    public void backup() {
        DatabaseUserFindReport report = DatabaseUserFindReport.backup(1);

        assertThat(report.getId(), is(1));
        assertThat(report.getType(), is(DatabaseType.BACKUP));
    }
}