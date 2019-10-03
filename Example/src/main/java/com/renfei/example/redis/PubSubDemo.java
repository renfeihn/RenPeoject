package com.renfei.example.redis;

/**
 * Created by Renfei on 2019/7/3.
 */

import redis.clients.jedis.Jedis;

public class PubSubDemo {
    public static void main(String[] args) {

        Subscriber subscriber = new Subscriber();
        SubThread subThread = new SubThread();
        subThread.setSubscriber(subscriber);
        subThread.start();

        SubThread2 subThread2 = new SubThread2();
        subThread2.start();

        Publisher publisher = new Publisher();
        publisher.start();

        subscriber.unsubscribe();

        publisher.start();

    }
}

