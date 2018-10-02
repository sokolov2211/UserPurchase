package com.sokolov.reports;

import com.sokolov.database.PostgresManager;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

/**
 * Created by admin on 01.10.2018.
 */
public class PurchaseReport extends ReportAbstract {
    public PurchaseReport() {
        this.reportName = "purchase_report_";
    }

    @Override
    protected String getQuery() {
        Date beginPeriod = Timestamp.from(Instant.now().minusSeconds(3600));
        String query = String.format("select productId, CAST(sum(price) as text) as productSum " +
                "from purchase " +
                "where operationDate >= '%s'" +
                " group by productId", beginPeriod);
        return query;
    }


}
