package com.sokolov.events;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

/**
 * Created by admin on 26.09.2018.
 */
public abstract class UserEvent {
    public String userId;
    public Date sessionTime;

    public UserEvent() {
        userId = UserEventHelper.generateRandomString(64);
        sessionTime = Timestamp.from(Instant.now());
    }
}
