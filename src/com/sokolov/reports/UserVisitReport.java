package com.sokolov.reports;

import com.sokolov.database.PostgresManager;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

/**
 * Created by admin on 02.10.2018.
 */
public class UserVisitReport extends ReportAbstract {
    public UserVisitReport() {
        this.reportName = "user_visit_report_";
    }

    @Override
    protected String getQuery() {
        Date beginPeriod = Timestamp.from(Instant.now().minusSeconds(5*60));
        String query = String.format("select distinct userId " +
                "from sessionHistory " +
                "where operationDate <= '%s' and " +
                "operationDate - duration * interval '1 second' >= '%s'", beginPeriod, beginPeriod);
        return query;
    }
}
