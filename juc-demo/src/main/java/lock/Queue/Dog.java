package lock.Queue;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-23
 */
public class Dog extends Pet{

    public static final String TYPE = "dog";


    public Dog() {
    }

    public Dog(String name) {
        super(TYPE, name);
    }
}
