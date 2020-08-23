package lock.Queue;

import java.util.LinkedList;
import java.util.Queue;


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
public class DogCatQueue {

    private Queue<PetEnter> dogQue;

    private Queue<PetEnter> catQue;

    private int count;

    public DogCatQueue() {
        this.dogQue = new LinkedList<>();
        this.catQue = new LinkedList<>();
        this.count = 0;
    }

    public void add(Pet pet){

        if (pet == null)return;

        String type = pet.getType();

        if (type.equals(Dog.TYPE)){
            dogQue.add(new PetEnter(pet,count++));
        }else if (type.equals(Cat.TYPE)){
            catQue.add(new PetEnter(pet,count++));
        }else {
            throw new IllegalThreadStateException("Pet must is dog or cat");
        }

    }

    //用户可以调用 pollAll 方法将队列中的 cat 和 dog 按照进队列的先后顺序依次弹出
    public Pet pollAll(){
        if (!dogQue.isEmpty() && !catQue.isEmpty()){

            if (dogQue.peek().getCount() < catQue.peek().getCount()){
                return dogQue.poll().getPet();
            }else {
                return catQue.poll().getPet();
            }
        }else if (!dogQue.isEmpty()){
            return dogQue.poll().getPet();
        }else if (!catQue.isEmpty()){
            return catQue.poll().getPet();
        }else {
            throw new NullPointerException(buildErrorMessage(Dog.TYPE, Cat.TYPE));
        }
    }

    //用户可以调用 pollDog 方法将队列中的 dog 按照进队列的先后顺序依次弹出
    public Pet pollDog() {

        if (dogQue.isEmpty())
            throw new NullPointerException(buildErrorMessage(Dog.TYPE));
        else
            return dogQue.poll().getPet();
    }

    //用户可以调用 pollCat 方法将队列中的 cat 按照进队列的先后顺序依次弹出
    public Pet pollCat(){

        if (catQue.isEmpty())
            throw new NullPointerException(buildErrorMessage(Cat.TYPE));
        else
            return catQue.poll().getPet();

    }

    //用户可以调用 isEmpty 方法检查队列中是否还有 dog 或 cat
    public boolean isEmpty(){
        return dogQue.isEmpty() && catQue.isEmpty();
    }

    //用户可以调用 isDogEmpty 方法检查队列中是否还有 dog
    public boolean isDogEmpty(){
        return dogQue.isEmpty();
    }
    //用户可以调用 isCatEmpty 方法检查队列中是否还有 cat
    public boolean isCatEmpty(){
        return catQue.isEmpty();
    }


    private String buildErrorMessage(String ... params){

        StringBuilder sb = new StringBuilder();

        for (String param : params) {
            sb.append(param).append("、");
        }

        sb.deleteCharAt(sb.length()-1);

        sb.append(" queue is null");

        return sb.toString();
    }

}
