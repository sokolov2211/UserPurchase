package com.sokolov.balancer;

import com.sokolov.events.*;
import java.util.Queue;
import java.util.LinkedList;


/**
 * Created by admin on 26.09.2018.
 * Source http://etutorials.org/Programming/Java+performance+tuning/Chapter+10.+Threading/10.8+Load+Balancing/
 */
public class PassiveRequestQueue
{
    //The queue of requests
    Queue<UserEvent> queue = new LinkedList<UserEvent>();

    public synchronized void acceptRequest(UserEvent r)
    {
        //Add to the queue, then notify all processors waiting
        //on the releaseRequest(  ) method
        queue.add(r);
        notify(  );
    }

    public synchronized UserEvent releaseRequest(  )
    {
        for(;;)
        {
            //if the queue is empty, just go back into the wait call
            if (queue.isEmpty(  ))
                try {wait(  );} catch (InterruptedException e){  }
            //Need to check again if the queue is empty, in case
            //we were interrupted
            if (!queue.isEmpty(  ))
                return (UserEvent) queue.poll();
        }
    }
}