package liu.hope.redis6379;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Redis6379Application {

    public static void main(String[] args) {
        SpringApplication.run(Redis6379Application.class, args);
    }

}
