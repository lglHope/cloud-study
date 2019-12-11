package liu.hope.zookeeper2181;


import liu.hope.zookeeper2181.listen.WatcherListen;
import liu.hope.zookeeper2181.util.ZkUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Zookeeper2181ApplicationTests {

    @Resource
    private ZkUtil zkUtil;

    @Test
    public void contextLoads() {
        zkUtil.exists("/mynode", new WatcherListen());
        zkUtil.deleteNode("/mynode");
        boolean world = zkUtil.createNode("/mynode", "world");
        System.out.println(world);

    }

}
