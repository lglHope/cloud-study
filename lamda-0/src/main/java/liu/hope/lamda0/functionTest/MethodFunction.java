package liu.hope.lamda0.functionTest;

import liu.hope.lamda0.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Hope
 * @version 1.0
 * TODO: 调用User工资比较方法，查询出工资大于3000的用户
 * @createTime 2019/12/17 10:26
 */
public class MethodFunction {


    public static void main(String[] args) {
        List<User> users =  new ArrayList<>();
        users.add(new User(1100));
        users.add(new User(2100));
        users.add(new User(3100));
        users.add(new User(4100));

        // 调用lamda表达式的方法，第一种：使用匿名内部类
        second(users, new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getSalary() > 3000;
            }
        });

        // 第二种：使用lamda表达式把匿名内部类优化
        // user是test()方法的输入参数
        // user.getSalary() > 3000是test()方法的方法体  实际上应该是 return user.getSalary() > 3000
        Predicate<User> predicate = user -> user.getSalary() > 3000;
        second(users, predicate);
        second(users, user -> user.getSalary() > 3000);

        // 第三种：方法引用
        // 在某个类中写一个方法，方法名任取，但是参数类型必须是User和返回值必须是boolean
        // 此处我写在User类中
        // public static boolean checkSalary(User user) {
        //        return user.getSalary() > 3000;
        //    }

        Predicate<User> predicate2 = User::checkSalary;
        second(users, predicate2);
        second(users, User::checkSalary);

        // 所以这里 User::checkSalary等同于user -> user.getSalary() > 3000？？？
        // 相当于写了一个类实现了Predicate接口并重写test方法，然后Predicate多态引用接收？
        // 也是就创建了一个真正的类而不是匿名内部类？
    }

    /**
     * 此处是最原始的代码
     */
    private static void first(List<User> users){
        for (User user : users) {
            if (user.getSalary() > 3000) {
                System.out.println(user);
            }
        }
    }

    /**
     * 此处是优化，使用函数式接口
     */
    private static void second(List<User> users, Predicate<User> predicate){
        for (User user : users) {
            // 上面所有的方法都是创建了一个对象，具体的实现还是要调用接口的方法，来达到判断的效果
            if (predicate.test(user)) {
                System.out.println(user);
            }
        }
    }


}
