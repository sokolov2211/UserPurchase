package com.sokolov.events;

import java.util.Random;

/**
 * Created by admin on 26.09.2018.
 */
public class NewLevelEvent extends UserEvent {
    public int level;


    public NewLevelEvent() {
        super();

        Random randomLevel = new Random();
        level = randomLevel.nextInt(100);
    }
}
