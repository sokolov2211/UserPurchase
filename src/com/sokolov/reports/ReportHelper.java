package com.sokolov.reports;

import com.sokolov.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import com.opencsv.CSVWriter;

/**
 * Created by admin on 02.10.2018.
 */
public class ReportHelper {
    public static void writeResultSetToCSV(ResultSet result, String fileName) {
        String reportFolder = Main.configManager.getReportFolder();
        checkFolder(reportFolder);
        String filePath = reportFolder + "\\" + fileName;
        try{
            CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
            csvWriter.writeAll(result, true);
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private synchronized static void checkFolder(String reportFolder) {
        File directory = new File(reportFolder);
        if (! directory.exists()) {
            directory.mkdirs();
        }
    }
}
