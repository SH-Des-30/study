package lock.Queue;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-23
 */
public class DogCatQueueTest {


    public static void main(String[] args) {
        DogCatQueue dogCatQueue=new DogCatQueue();
        Pet dog1=new Dog("dog1");
        Pet cat1=new Cat("cat1");
        Pet dog2=new Dog("dog2");
        Pet cat2=new Cat("cat2");
        Pet dog3=new Dog("dog3");
        Pet cat3=new Cat("cat3");

        dogCatQueue.add(dog1);
        dogCatQueue.add(cat1);
        dogCatQueue.add(dog2);
        dogCatQueue.add(cat2);
        dogCatQueue.add(dog3);
        dogCatQueue.add(cat3);

        dogCatQueue.add(dog1);
        dogCatQueue.add(cat1);
        dogCatQueue.add(dog2);
        dogCatQueue.add(cat2);
        dogCatQueue.add(dog3);
        dogCatQueue.add(cat3);

        dogCatQueue.add(dog1);
        dogCatQueue.add(cat1);
        dogCatQueue.add(dog2);
        dogCatQueue.add(cat2);
        dogCatQueue.add(dog3);
        dogCatQueue.add(cat3);


        System.out.println("===========isEmpty=============");
        while (!dogCatQueue.isEmpty()){
            System.out.print(dogCatQueue.pollAll().getName()+" ");
        }
        System.out.println();
//        System.out.println("===========isDogEmpty=============");
//
//        while (!dogCatQueue.isDogEmpty()){
//            System.out.print(dogCatQueue.pollDog().getName()+" ");
//        }



    }
}
