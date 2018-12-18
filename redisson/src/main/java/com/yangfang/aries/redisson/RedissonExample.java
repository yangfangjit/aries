package com.yangfang.aries.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

/**
 * Redisson - distributed Java objects and services (Set, Multimap, SortedSet, Map, List, Queue, Deque, Semaphore, Lock,
 * AtomicLong, Map Reduce, Publish / Subscribe, Bloom filter, Spring Cache, Tomcat Session Manager, Scheduler service,
 * JCache API) on top of Redis server. State of the Art Redis Java client <a href=https://redisson.org/>
 *
 * @author 幽明
 * @serial 2018/12/11
 */
public class RedissonExample {

    public static void main(String[] args) {
        Config config = new Config();
        config.setTransportMode(TransportMode.EPOLL);
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("redis://127.0.0.1:7181");

        RedissonClient redisson = Redisson.create(config);
    }
}
