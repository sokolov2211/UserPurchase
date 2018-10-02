package com.sokolov.events;

import java.util.Random;

/**
 * Created by admin on 26.09.2018.
 */
public class SessionEndEvent extends UserEvent {
    public int duration;

    public SessionEndEvent() {
        super();

        Random randomDuration = new Random();
        duration = randomDuration.nextInt(1000000);
    }
}
