package liu.hope.my_demo_boot.config;

import liu.hope.my_demo_boot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //通过@EnableWebMvcSecurity注解开启Spring Security的功能
    //继承WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节
    //configure(HttpSecurity http)方法
    //通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。
    //通过formLogin()定义当需要用户登录时候，转到的登录页面。
    //configureGlobal(AuthenticationManagerBuilder auth)方法，在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为USER

    @Resource
    private UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    // 匹配访问的路径
                    .antMatchers("/login")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    // 未登录时跳转的路径
                    .loginPage("/login/login")
                    // 登陆表单提交请求,当这个不写时  默认与loginPage一样
                    .loginProcessingUrl("/login/login")
                    .permitAll()
                    // 登录成功之后跳转的路径
//                    .defaultSuccessUrl("/getMyFriend")
                    .successHandler((request, response, authentication) -> {
                        //  实现登录成功之后跳转回原先登录前的页面
                        response.setContentType("application/json;charset=utf-8");
                        HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
                        SavedRequest saveRequest = httpSessionRequestCache.getRequest(request, response);
                        String redirectUrl = saveRequest.getRedirectUrl();
                        response.sendRedirect(redirectUrl);
                    })
//                    .failureHandler((request, response, authentication) -> {
//                        response.setContentType("application/json;charset=utf-8");
//                        response.getWriter().write("登录失败！");
//                    })
                    .and()
                .logout()
                    .permitAll()
                    .and()
                // 开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                    .tokenValiditySeconds(60 * 60 * 24 * 7)
                    .and()
                .csrf()
                    .disable();
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
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(new MyPasswordEncoder())
//                .withUser("hope").password("123456").roles("USER");
        auth.userDetailsService(userService)
                .passwordEncoder(new MyPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                // 忽略不需要身份验证的uri
                .ignoring()
                // 如果有静态文件
                .mvcMatchers("/css/**", "/js/**");
    }

    @Autowired
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}