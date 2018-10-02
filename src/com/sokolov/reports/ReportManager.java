package com.sokolov.reports;

/**
 * Created by admin on 02.10.2018.
 */
public class ReportManager implements Runnable {
    private PurchaseReport purchaseReport;
    private UserVisitReport userVisitReport;

    public ReportManager() {
        purchaseReport = new PurchaseReport();
        userVisitReport = new UserVisitReport();
    }

    public void run() {
        try {
            for ( ; ; ) {
                Thread.currentThread().sleep(5 *  1000);
                purchaseReport.formReport();
                userVisitReport.formReport();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
