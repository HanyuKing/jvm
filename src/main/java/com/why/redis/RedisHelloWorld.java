package com.why.redis;

import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;

public class RedisHelloWorld {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("47.254.28.50", 6302);
        jedis.auth("hanyuking_redis");
        jedis.set("name", "hanyu");
        System.out.println("name: " + jedis.get("name"));
        jedis.disconnect();


        SimpleDateFormat sdf = new SimpleDateFormat();
        /*sdf.parse("");*/
    }
}
