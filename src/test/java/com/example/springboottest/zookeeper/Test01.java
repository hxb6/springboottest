package com.example.springboottest.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Package: com.example.springboottest.zookeeper
 * @Author: HeXiaoBo
 * @CreateDate: 2019/11/15 23:07
 * @Description:
 **/
public class Test01 {

    /**
     * ip地址
     */
    private static final String ZK_ADDR = "127.0.0.1:2181";

    /**
     * 超时时间 5s
     */
    private static final Integer TIMEOUT = 5000;

    /**
     * 父节点名称
     */
    private static final String PARENTPATH = "spring-boot";

    private static final String SEPARATOR = "/";

    /**
     * 计数器
     */
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void getData() throws Exception {
        //创建zookeeper
        ZooKeeper zooKeeper = new ZooKeeper(ZK_ADDR, TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("zk已经连接");
                    countDownLatch.countDown();
                }
            }
        });

        countDownLatch.await();
        System.out.println("开始获取节点信息");

        List<String> children = zooKeeper.getChildren(SEPARATOR + PARENTPATH, null);
        for (String child : children) {
            String childPath = SEPARATOR + PARENTPATH + SEPARATOR + child;
            byte[] data = zooKeeper.getData(childPath, null, new Stat());
            System.out.println(new String(data));
        }

    }
}
