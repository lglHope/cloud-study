package liu.hope.zookeeper2181.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import liu.hope.zookeeper2181.service.IUserService;

/**
 * @author Hope
 * @version 1.0
 * TODO:
 * @createTime 2019/12/12 16:10
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public String getOne() {
        return "one";
    }

    @Override
    public String getTwo() {
        return "two";
    }
}
