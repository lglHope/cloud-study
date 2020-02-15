package liu.hope.my_demo_boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //通过@EnableWebMvcSecurity注解开启Spring Security的功能
    //继承WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节
    //configure(HttpSecurity http)方法
    //通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。
    //通过formLogin()定义当需要用户登录时候，转到的登录页面。
    //configureGlobal(AuthenticationManagerBuilder auth)方法，在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为USER

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // 允许未登录访问的路径
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                // 未登录时跳转的路径
                .loginPage("/login/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //可以设置内存指定的登录的账号密码,指定角色
        //不加.passwordEncoder(new MyPasswordEncoder())
        //就不是以明文的方式进行匹配，会报错
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("USER");
        //.passwordEncoder(new MyPasswordEncoder())。
        //这样，页面提交时候，密码以明文的方式进行匹配。
        auth
                .inMemoryAuthentication()
                .passwordEncoder(new MyPasswordEncoder())
                .withUser("hope").password("123456").roles("USER");
    }

}