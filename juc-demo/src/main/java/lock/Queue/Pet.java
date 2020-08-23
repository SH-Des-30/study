package lock.Queue;

/**
 *
 * 1. 用户可以调用 add 方法将 cat 或者 dog 放入队列中
 * 2. 用户可以调用 pollAll 方法将队列中的 cat 和 dog 按照进队列的先后顺序依次弹出
 * 3. 用户可以调用 pollDog 方法将队列中的 dog 按照进队列的先后顺序依次弹出
 * 4. 用户可以调用 pollCat 方法将队列中的 cat 按照进队列的先后顺序依次弹出
 * 5. 用户可以调用 isEmpty 方法检查队列中是否还有 dog 或 cat
 * 6. 用户可以调用 isDogEmpty 方法检查队列中是否还有 dog
 * 7. 用户可以调用 isCatEmpty 方法检查队列中是否还有 cat
 * 提示：1、猫狗队列是一个类。并不指定是某个队列
 *      2、所有方法均为合法操作.
 *
 * @author sishijie@winployee.com
 * @since 2020-08-23
 */
public class Pet {

    private String type;
    private String name;

    public Pet() {
    }


    public Pet(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
