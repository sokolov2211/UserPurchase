package com.sokolov.reports;

import com.sokolov.database.PostgresManager;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Created by admin on 01.10.2018.
 */
public abstract class ReportAbstract {
    protected String reportName;

    public void formReport() {
        String query = this.getQuery();
        PostgresManager dbManager = PostgresManager.getInstance();
        ResultSet result = dbManager.execSelectQuery(query);
        ReportHelper.writeResultSetToCSV(result, this.getReportName());
    }

    protected abstract String getQuery();

    protected String getReportName() {
        Date sessionTime = Timestamp.from(Instant.now());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return reportName + formatter.format(sessionTime) + ".csv";
    }
}
