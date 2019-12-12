package liu.hope.zookeeper2181;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class Zookeeper2181Application {

    public static void main(String[] args) {
        SpringApplication.run(Zookeeper2181Application.class, args);
    }

}
