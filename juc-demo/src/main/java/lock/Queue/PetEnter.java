package lock.Queue;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-23
 */
public class PetEnter {

    private Pet pet;

    private int count;

    public PetEnter(Pet pet, int count) {
        this.pet = pet;
        this.count = count;
    }

    public Pet getPet() {
        return pet;
    }

    public int getCount() {
        return count;
    }

    public String getPetType(){
        return pet.getType();
    }
}
