package liu.hope.zookeeper2181.util;

import liu.hope.zookeeper2181.listen.WatcherListen;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hope
 * @version 1.0
 * TODO:
 * @createTime 2019/12/11 16:15
 */
@Component
public class ZkUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZkUtil.class);

    @Resource
    private ZooKeeper zookeeper;

    /**
     * 判断指定节点是否存在
     * @param path
     * @param needWatch  指定是否复用zookeeper中默认的Watcher,创建zookeeper对象的时候使用的默认Watcher
     * @return
     */
    public Stat exists(String path, boolean needWatch){
        try {
            return zookeeper.exists(path,needWatch);
        } catch (Exception e) {
            logger.error("【断指定节点是否存在异常】{},{}",path,e);
            return null;
        }
    }

    /**
     *  检测结点是否存在 并设置监听事件
     *      三种监听类型： 创建，删除，更新
     *      exists操作上的watch，在被监视的Znode创建、删除或数据更新时被触发。
     *
     * @param path
     * @param watcher  传入指定的监听类
     * @return
     */
    public Stat exists(String path, Watcher watcher){
        try {
            return zookeeper.exists(path,watcher);
        } catch (Exception e) {
            logger.error("【断指定节点是否存在异常】{},{}",path,e);
            return null;
        }
    }

    /**
     * 创建持久化节点
     * @param path
     * @param data
     */
    public boolean createNode(String path, String data){
        try {
            zookeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        } catch (Exception e) {
            logger.error("【创建持久化节点异常】{},{},{}",path,data,e);
            return false;
        }
    }


    /**
     * 修改持久化节点
     * @param path
     * @param data
     */
    public boolean updateNode(String path, String data){
        try {
            //zk的数据版本是从0开始计数的。如果客户端传入的是-1，则表示zk服务器需要基于最新的数据进行更新。如果对zk的数据节点的更新操作没有原子性要求则可以使用-1.
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zookeeper.setData(path,data.getBytes(),-1);
            return true;
        } catch (Exception e) {
            logger.error("【修改持久化节点异常】{},{},{}",path,data,e);
            return false;
        }
    }

    /**
     * 删除持久化节点
     * @param path
     */
    public boolean deleteNode(String path){
        try {
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zookeeper.delete(path,-1);
            return true;
        } catch (Exception e) {
            logger.error("【删除持久化节点异常】{},{}",path,e);
            return false;
        }
    }

    /**
      * 获取当前节点的子节点(不包含孙子节点)
      * @param path 父节点path
      */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException{
        List<String> list = zookeeper.getChildren(path, false);
        return list;
    }

    /**
     * 获取指定节点的值
     *
     * getData操作上的watch，在被监视的Znode删除或数据更新时被触发。
     * 在被创建时不能被触发，因为只有Znode一定存在，getData操作才会成功。
     * @param path
     * @return
     */
    public String getData(String path,Watcher watcher){
        try {
            Stat stat=new Stat();
            byte[] bytes = zookeeper.getData(path,watcher,stat);
            return  new String(bytes);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * getChildren操作上的watch，在被监视的Znode的子节点创建或删除，或是这个Znode自身被删除时被触发。
     * 可以通过查看watch事件类型来区分是Znode，还是他的子节点被删除：NodeDelete表示Znode被删除，
     * NodeDeletedChanged表示子节点被删除。
     */
    public List<String> getChildren(String path,Watcher watcher) {
        try {
            List<String> childrens = zookeeper.getChildren(path, watcher);
            return  childrens;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 测试方法  初始化
     */
    @PostConstruct
    public  void init(){
        String path="/zk-watcher-2";
        logger.info("【执行初始化测试方法。。。。。。。。。。。。】");
        createNode(path,"测试");
        String value=getData(path,new WatcherListen());
        logger.info("【执行初始化测试方法getData返回值。。。。。。。。。。。。】={}",value);

        // 删除节点出发 监听事件
        deleteNode(path);

    }

}

