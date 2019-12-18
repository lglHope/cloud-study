package liu.hope.zookeeper2181;


import liu.hope.zookeeper2181.listen.WatcherListen;
import liu.hope.zookeeper2181.util.ZkUtil;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Zookeeper2181ApplicationTests {

    @Resource
    private ZkUtil zkUtil;

    @Resource
    private ZkClient zkClient;

    @Test
    public void contextLoads() {
        zkUtil.exists("/mynode", new WatcherListen());
        zkUtil.deleteNode("/mynode");
        boolean world = zkUtil.createNode("/mynode", "world");
        System.out.println(world);
//        zkClient.createPersistent("/test", "test");
//        Map.Entry<List<ACL>, Stat> acl = zkClient.getAcl("/test");


    }

}
