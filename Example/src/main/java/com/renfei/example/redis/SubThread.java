package com.renfei.example.redis;

/**
 * Created by Renfei on 2019/7/3.
 */
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class SubThread extends Thread {
    private Subscriber subscriber;

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public static final String CHANNEL = "mychannel";

    public SubThread() {
        super("SubThread");
    }

    @Override
    public void run() {
//        System.out.println(String.format("subscribe redis, channel %s, thread will be blocked", CHANNEL));
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getRedisUtil().getJedis();
            jedis.subscribe(subscriber, CHANNEL);
        } catch (Exception e) {
            System.out.println(String.format("subsrcibe channel error, %s", e));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
