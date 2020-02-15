package liu.hope.my_demo_boot.mapper;

import liu.hope.my_demo_boot.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserRepository {

    public User findByName(String username){
        if ("hope".equals(username)) {
            return new User("hope", "1");
        }
        return null;
    }
}
