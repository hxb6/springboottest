package com.example.springboottest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Package: com.example.springboottest
 * @Author: HeXiaoBo
 * @CreateDate: 2019/11/15 22:30
 * @Description: Spring Boot启动后的回调方法
 **/
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Value("${server.port}")
    private String serverPort;

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

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
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
        System.out.println("开始创建节点");
        Stat exists = zooKeeper.exists(SEPARATOR + PARENTPATH, null);
        //判断是否存在父节点 不存在则先创建父节点
        if(exists == null){
            zooKeeper.create(SEPARATOR + PARENTPATH, PARENTPATH.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        String s = zooKeeper.create(SEPARATOR + PARENTPATH + SEPARATOR + serverPort, serverPort.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(s);
        zooKeeper.close();
    }
}
