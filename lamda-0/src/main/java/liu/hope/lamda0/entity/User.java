package liu.hope.lamda0.entity;

/**
 * @author Hope
 * @version 1.0
 * TODO:
 * @createTime 2019/12/17 10:19
 */
public class User{
    private String name;
    private int age;
    private double salary;
    private char sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, double salary, char sex) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.sex = sex;
    }

    public User(double salary) {
        this.salary = salary;
    }

    public static boolean checkSalary(User user) {
        return user.getSalary() > 3000;
    }
}
