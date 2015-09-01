package dataacceess;

/**
 * Created by Dotin School1 on 9/1/2015.
 */
public class A {
    private static A ourInstance = new A();

    public static A getInstance() {
        return ourInstance;
    }

    private A() {
    }


}
