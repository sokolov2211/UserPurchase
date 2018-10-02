package com.sokolov.generators;

import com.sokolov.balancer.ActiveRequestQueue;
import com.sokolov.events.NewLevelEvent;
import com.sokolov.events.PurchaseEvent;
import com.sokolov.events.SessionEndEvent;
import com.sokolov.events.UserEvent;

import java.awt.*;
import java.util.Observable;
import java.util.Random;

/**
 * Created by admin on 20.09.2018.
 */
public class EventGeneratorsManger implements Runnable {

    private ActiveRequestQueue eventProccesor;
    private Random eventNumberGenerator;

    public EventGeneratorsManger(ActiveRequestQueue eventProccesor) {
        this.eventNumberGenerator = new Random();
        this.eventProccesor = eventProccesor;
    }
    public void run() {
        try {
            for( ; ; ) {
                this.generateRandomEvent();
                Thread.currentThread().sleep(10);
            }
        } catch (Exception e) {

        }
    }

    private  void generateRandomEvent() {
        UserEvent event;
        int eventNumber = eventNumberGenerator.nextInt(300);
        if (eventNumber <= 100) {
            event = new PurchaseEvent();
        } else if ((eventNumber > 100)&&(eventNumber < 200)) {
            event = new NewLevelEvent();
        } else {
            event = new SessionEndEvent();
        }
        this.eventProccesor.acceptRequest(event);
    }
}
