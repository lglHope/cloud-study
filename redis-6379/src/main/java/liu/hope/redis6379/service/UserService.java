package liu.hope.redis6379.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Hope
 * @version 1.0
 * TODO:
 * @createTime 2019/12/10 10:05
 */
@Service
public class UserService {

    /**
     * @CachePut：方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前
     *            不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执
     *            行结果以键值对的形式存入指定的缓存中。
     * public @interface CachePut {
     *     String[] value();              //缓存的名字，可以把数据写到多个缓存
     *     String key() default "";       //缓存key，如果不指定将使用默认的KeyGenerator生成，后边介绍
     *     String condition() default ""; //满足缓存条件的数据才会放入缓存，condition在调用方法之前和之后都会判断
     *     String unless() default "";    //用于否决缓存更新的，不像condition，该表达只在方法执行之后判断，此时可以拿到返回值result进行判断了
     * }
     *
     *
     * @Cacheable：标记在一个方法上，也可以标记在一个类上。主要是缓存标注对象的返回结果，
     *             标注在方法上缓存该方法的返回值，标注在类上，缓存该类所有的方法返回值。
     * 参数： value缓存名、 key缓存键值、 condition满足缓存条件、unless否决缓存条件
     * public @interface Cacheable {
     *     String[] value();             //请参考@CachePut
     *     String key() default "";      //请参考@CachePut
     *     String condition() default "";//请参考@CachePut
     *     String unless() default "";   //请参考@CachePut
     *
     * @CacheEvict：从缓存中移除相应数据。
     * public @interface CacheEvict {
     *     String[] value();                        //请参考@CachePut
     *     String key() default "";                 //请参考@CachePut
     *     String condition() default "";           //请参考@CachePut
     *     boolean allEntries() default false;      //是否移除所有数据
     *     boolean beforeInvocation() default false;//是调用方法之前移除/还是调用之后移除
     * }
     *
     * 提供的SpEL上下文数据
     *      名字  	           位置	                 描述     	         示例
     *  methodName           root对象          当前被调用的方法名        #root.methodName
     *    method             root对象          当前被调用的方法          #root.method.name
     *    target             root对象          当前被调用的目标对象      #root.target
     *  targetClass          root对象          当前被调用的目标对象类    #root.targetClass
     *   args                root对象          当前被调用的方法的参数列表 #root.args[0]
     *  caches               root对象          当前方法调用使用的缓存列表（如@Cacheable(value={"cache1", "cache2"})），则有两个cache #root.caches[0].name
     * argument name         执行上下文        当前被调用的方法的参数，如findById(Long id)，我们可以通过#id拿到参数 #user.id
     *  result               执行上下文        方法执行后的返回值（仅当方法执行之后的判断有效，如‘unless’，'cache evict'的beforeInvocation=false） #result
     *
     * 运行流程：
     * 首先执行@CacheEvict（如果beforeInvocation=true且condition 通过），如果allEntries=true，则清空所有
     * 接着收集@Cacheable（如果condition 通过，且key对应的数据不在缓存），放入cachePutRequests（也就是说如果cachePutRequests为空，则数据在缓存中）
     * 如果cachePutRequests为空且没有@CachePut操作，那么将查找@Cacheable的缓存，否则result=缓存数据（也就是说只要当没有cache put请求时才会查找缓存）
     * 如果没有找到缓存，那么调用实际的API，把结果放入result
     * 如果有@CachePut操作(如果condition 通过)，那么放入cachePutRequests
     * 执行cachePutRequests，将数据写入缓存（unless为空或者unless解析结果为false）；
     * 执行@CacheEvict（如果beforeInvocation=false 且 condition 通过），如果allEntries=true，则清空所有
     *
     *
     */

    @CachePut(value = "USERCACHE", key = "#key")
    public String put(String key, String value){
        System.out.println("CachePut方法执行了---------");
        return value;
    }

    @Cacheable(value = "USERCACHE", key = "#key")
    public String able(String key, String value){
        System.out.println("Cacheable方法执行了---------");
        return value;
    }

    @CacheEvict(value = "USERCACHE", key = "#key")
    public String evict(String key, String value){
        System.out.println("CacheEvict方法执行了---------");
        return value;
    }

    @Cacheable(value = "USERCACHE", key = "#key")
    public String get(String key, String value){
        System.out.println("Cacheget方法执行了---------");
        return value;
    }

}
