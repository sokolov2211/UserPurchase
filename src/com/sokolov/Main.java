package com.sokolov;

import com.sokolov.balancer.ActiveRequestQueue;
import com.sokolov.events.NewLevelEvent;
import com.sokolov.events.PurchaseEvent;
import com.sokolov.events.UserEvent;
import com.sokolov.generators.EventGeneratorsManger;
import com.sokolov.reports.PurchaseReport;
import com.sokolov.reports.ReportManager;

import java.util.ArrayList;


public class Main {
    public static ArrayList<Thread> curThreadList;
    public static AppConfigManager configManager;

    public static void main(String[] args) {
        configManager = new AppConfigManager("Config.xml");
        ActiveRequestQueue eventQueue = new ActiveRequestQueue(configManager.getNumHandlerThread());
        int numEventGenerators = configManager.getNumGeneratorThread();
        for (int i = 0; i < numEventGenerators - 1; i++) {
            Thread eventGeneratroThread = new Thread(new EventGeneratorsManger(eventQueue));
            eventGeneratroThread.start();
        }
        Thread reportGeneratorThread = new Thread(new ReportManager());
        reportGeneratorThread.start();
    }
}
