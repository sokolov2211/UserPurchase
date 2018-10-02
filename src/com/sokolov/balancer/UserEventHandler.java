package com.sokolov.balancer;

import com.sokolov.database.PostgresManager;
import com.sokolov.events.NewLevelEvent;
import com.sokolov.events.PurchaseEvent;
import com.sokolov.events.SessionEndEvent;
import com.sokolov.events.UserEvent;
import javafx.geometry.Pos;

/**
 * Created by admin on 26.09.2018.
 */
public class UserEventHandler {
    public void processRequest(UserEvent event) {
        String className = event.getClass().getSimpleName();
        switch (className) {
            case "NewLevelEvent":
                handlerNewLevelEvent(event);
                break;
            case "PurchaseEvent":
                handlerPurchaseEvent(event);
                break;
            case "SessionEndEvent":
                handlerSessionEndEvent(event);
                break;
        }
    }

    private void handlerNewLevelEvent(UserEvent event) {
        NewLevelEvent eventLevel = (NewLevelEvent) event;
        String insertQuery = String.format("INSERT INTO  levelUpHistory (userId, level, operationDate) " +
                "VALUES ('%s', %d, '%s')", eventLevel.userId, eventLevel.level, eventLevel.sessionTime);
        this.insertRecord(insertQuery);
    }

    private void handlerPurchaseEvent(UserEvent event) {
        PurchaseEvent eventPurchase = (PurchaseEvent) event;
        String price = String.format("%.2f", eventPurchase.price).replace(',', '.');
        String insertQuery = String.format("INSERT INTO  purchase (userId, ProductId, Price, operationDate) " +
                "VALUES ('%s', %d, %s , '%s')",
                eventPurchase.userId, eventPurchase.productId, price , eventPurchase.sessionTime);
        this.insertRecord(insertQuery);
    }

    private void  handlerSessionEndEvent(UserEvent event) {
        SessionEndEvent eventSessionEnd = (SessionEndEvent) event;
        String insertQuery = String.format("INSERT INTO sessionHistory (userId, duration, operationDate) " +
                "VALUES ('%s', %d, '%s')",
                eventSessionEnd.userId, eventSessionEnd.duration, eventSessionEnd.sessionTime);
        this.insertRecord(insertQuery);
    }

    private void insertRecord(String insertQuery) {
        PostgresManager dbManager = PostgresManager.getInstance();
        dbManager.execQuery(insertQuery);
    }
}
