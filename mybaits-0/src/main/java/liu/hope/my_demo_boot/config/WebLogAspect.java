package liu.hope.my_demo_boot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
//@Order(5)  // 设置切面的优先级  值越小优先级越高   在切入点前的操作，按order的值由小到大执行  在切入点后的操作，按order的值由大到小执行
public class WebLogAspect {

    private Logger logger = LogManager.getLogger(getClass());
    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Pointcut("execution(public * *..*.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        // 请求处理开始时间
        threadLocal.set(System.currentTimeMillis());

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 请求处理结束时间
        long timeMillis = System.currentTimeMillis();
        // 请求处理时间
        logger.info("请求处理时间 ：" + (timeMillis - threadLocal.get()) + "ms");
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }

}