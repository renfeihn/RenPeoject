package com.renfei.example.redis;

/**
 * Created by Renfei on 2019/7/3.
 */
import redis.clients.jedis.Jedis;


public class SubThread2 extends Thread {
    private final Subscriber2 subscriber2 = new Subscriber2();

    public static final String CHANNEL = "mychannel";

    public SubThread2() {
        super("SubThread");
    }

    @Override
    public void run() {
//        System.out.println(String.format("subscribe redis, channel %s, thread will be blocked", CHANNEL));
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getRedisUtil().getJedis();
            jedis.subscribe(subscriber2, CHANNEL);
        } catch (Exception e) {
            System.out.println(String.format("subsrcibe channel error, %s", e));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
