package com.sokolov.balancer;

import com.sokolov.events.UserEvent;

/**
 * Created by admin on 26.09.2018.
 * Source: http://etutorials.org/Programming/Java+performance+tuning/Chapter+10.+Threading/10.8+Load+Balancing/
 */
public class ActiveRequestQueue
        //subclass the passive queue that holds the behavior
        //needed for managing the queue of requests
        extends PassiveRequestQueue
        //and make us able to run in our own thread
        implements Runnable
{
    int MAXIMUM_NUM_SERVERS=3;

    //Data for the public queue - a list of private servers
    ActiveRequestQueue[  ] servers;

    //Data for the private (internal) queues
    //the RequestProcessor
    UserEventHandler requestProcessor;
    //Retain a handle on my thread so that we can easily access
    //it if we need control
    Thread myThread;
    //Keep a handle on the 'public' queue - the one that
    //actually holds the objects
    ActiveRequestQueue queueServer;
    //Availability
    boolean isAvailable = true;

    //Internal queue object - processes requests
    private ActiveRequestQueue(ActiveRequestQueue q)
    {
        queueServer = q;
        requestProcessor=new UserEventHandler();
    }

    //External queue object - accepts requests and manages a queue of them
    public ActiveRequestQueue(int num_servers)
    {
        //Create a pool of queue servers and start them in their own threads
        servers = new ActiveRequestQueue[num_servers];
        Thread t;
        for (int i = servers.length-1; i>=0 ; i--)
        {
            servers[i] = new ActiveRequestQueue(this);
            (t = new Thread(servers[i])).start();
            servers[i].myThread = t;
        }
    }

    public synchronized void acceptRequest(UserEvent r)
    {
        //Override the super class accept to increase the number
        //of servers if they are all busy

        //If we already have the maximum number of threads,
        //just queue the request
        if (servers.length >= MAXIMUM_NUM_SERVERS)
        {
            super.acceptRequest(r);
            return;
        }

        //otherwise, if one of the servers is available, just queue
        //the request
        for (int i = servers.length-1; i>=0 ; i--)
        {
            if (servers[i].isAvailable(  ))
            {
                super.acceptRequest(r);
                return;
            }
        }

        //otherwise, increase the server pool by one, then queue the request
        Thread t;
        ActiveRequestQueue[  ] tmp_servers = servers;
        servers = new ActiveRequestQueue[tmp_servers.length+1];
        System.arraycopy(tmp_servers, 0, servers, 0, tmp_servers.length);
        servers[tmp_servers.length] = new ActiveRequestQueue(this);
        (t = new Thread(servers[tmp_servers.length])).start(  );
        servers[tmp_servers.length].myThread = t;
        super.acceptRequest(r);
    }

    public void run(  )
    {
        UserEvent event;
        //Private queues use this method.

        //Basically, we just ask the public server for a request.
        //The releaseRequest(  ) method blocks until one is available.
        //Then we process it and start again.
        for(;;)
        {
            event = queueServer.releaseRequest(  );
            isAvailable = false;
            requestProcessor.processRequest(event);
            isAvailable = true;
        }
    }

    public boolean isAvailable(  ) { return isAvailable;}
}