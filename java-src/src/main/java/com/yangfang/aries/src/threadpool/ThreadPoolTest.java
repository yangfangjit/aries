/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */

package com.yangfang.aries.src.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * thread pool test
 *
 * @author 幽明
 * @serial 2021/3/10
 */
public class ThreadPoolTest {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int COUNT_BITS = Integer.SIZE - 3;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    private static int ctlOf(int rs, int wc) { return rs | wc; }

    public static void main(String[] args) {

        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(0));
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(2));
        System.out.println(Integer.toBinaryString(3));

        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));
        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(TIDYING));

        System.out.println(RUNNING);
        System.out.println(SHUTDOWN);
        System.out.println(STOP);
        System.out.println(TIDYING);
        System.out.println(TERMINATED);
    }
}
