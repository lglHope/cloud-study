package liu.hope.zookeeper2181.config;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * @author Hope
 * @version 1.0
 * TODO:
 * @createTime 2019/12/11 16:00
 */
@Configuration
public class ZookeeperConfig {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConfig.class);

    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private int timeout;

    /**
     * 原生的zookeeper整合
     */
    @Bean(name = "zookeeper")
    public ZooKeeper zooKeeper(){
        ZooKeeper zooKeeper = null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            // 连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            // 可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(connectString, timeout, event -> {
                if(Watcher.Event.KeeperState.SyncConnected == event.getState()){
                    //如果收到了服务端的响应事件,连接成功
                    countDownLatch.countDown();
                    logger.info("【Watcher监听事件】={}",event.getState().name());
                    logger.info("【监听路径为】={}",event.getPath());
                    //  三种监听类型： 创建，删除，更新
                    logger.info("【监听的类型为】={}",event.getType());
                }
            });
            countDownLatch.await();
            logger.info("【初始化ZooKeeper连接状态....】={}",zooKeeper.getState());
        }catch (Exception e){
            logger.error("初始化ZooKeeper连接异常....】={}",e);
        }
        return zooKeeper;
    }

//    @Bean(name = "zkClient")
//    public ZkClient zkClient(){
//        return new ZkClient(new ZkConnection(connectString), timeout, new SerializableSerializer());
//    }

}
