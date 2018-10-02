package com.sokolov;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Created by admin on 01.10.2018.
 */
public class AppConfigManager {
    // Потоки
    private int numHandlerThread;
    private int numGeneratorThread;
    // Настройка БД
    private String databaseName;
    private String databasePort;
    private String databaseHost;
    private String databaseUser;
    private String databasePassword;
    // Настройка отчетов
    private String reportFolder;

    public AppConfigManager(String filePath) {
        parseXMLConfig(filePath);
    }

    public int getNumHandlerThread() {
        return  this.numHandlerThread;
    }

    public int getNumGeneratorThread() {
        return  this.numGeneratorThread;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public  String getDatabasePassword() {
        return  databasePassword;
    }

    public String getReportFolder() { return reportFolder; }

    private void parseXMLConfig(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            // Получение конфигурации базы данных
            Element nodeDatabase = (Element) doc.getElementsByTagName("database").item(0);
            databaseHost = nodeDatabase.getElementsByTagName("host").item(0)
                    .getTextContent();
            databasePort = nodeDatabase.getElementsByTagName("port").item(0)
                    .getTextContent();
            databaseName = nodeDatabase.getElementsByTagName("basename").item(0)
                    .getTextContent();
            databaseUser = nodeDatabase.getElementsByTagName("user").item(0)
                    .getTextContent();
            databasePassword = nodeDatabase.getElementsByTagName("password").item(0)
                    .getTextContent();
            // Получение конфигурации потоков
            Element nodeThreds = (Element) doc.getElementsByTagName("threads").item(0);
            numGeneratorThread = Integer.valueOf(nodeThreds.getElementsByTagName("counteventthread").item(0)
                    .getTextContent());
            numHandlerThread = Integer.valueOf(nodeThreds.getElementsByTagName("counthandlerthread").item(0)
                    .getTextContent());
            // Получение конфигурации отчета
            Element nodeReports = (Element) doc.getElementsByTagName("reports").item(0);
            reportFolder = nodeReports.getElementsByTagName("folder").item(0)
                    .getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
