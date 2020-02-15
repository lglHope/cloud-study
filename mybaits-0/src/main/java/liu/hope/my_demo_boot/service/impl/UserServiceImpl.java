package liu.hope.my_demo_boot.service.impl;

import liu.hope.my_demo_boot.entity.SecurityUser;
import liu.hope.my_demo_boot.entity.User;
import liu.hope.my_demo_boot.mapper.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
//        System.out.println("s:"+s);
//        System.out.println("username:"+user.getName()+";password:"+user.getPassword());
        return new SecurityUser(user);
    }
}