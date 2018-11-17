package com.yangfang.aries.src;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * block queue test
 *
 * @author 幽明
 * @serial 2018/11/15
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        BlockingQueue queue = new LinkedBlockingQueue();
        queue.contains(new Object());
    }
}
