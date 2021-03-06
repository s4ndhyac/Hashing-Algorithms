
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashing implements IHashingAlgorithm {
    int capacity;
    int size;
    ArrayList<LinkedList<Entry>> bucket;

    ChainedHashing(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        bucket = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++) {
            bucket.add(null);
        }
    }

    @Override
    public int hash(Integer key) {
        int result = key % capacity;
        return result;
    }

    @Override
    public int set(Integer key, Integer value) {
        int index = hash(key);
        int count = 0;
        if(bucket.get(index) == null) {
            LinkedList<Entry> chain = new LinkedList<>();
            chain.add(new Entry(key, value));
            bucket.set(index, chain);
            count++;
        } else {
            LinkedList<Entry> chain = bucket.get(index);
            boolean alreadyExist = false;
            for(Entry entry : chain) {
                count++;
                if(entry.key.equals(key)) {
                    entry.value = value;
                    alreadyExist = true;
                    break;
                }
            }
            if(!alreadyExist) {
                count++;
                bucket.get(index).add(new Entry(key, value));
            }
        }
        size++;
        return count;
    }


    @Override
    public int[] search(Integer key) {
        int index = hash(key);
        int result = -1;
        int count = 0;
        if(bucket.get(index) != null) {
            for(Entry temp : bucket.get(index)) {
                count++;
                if(temp.key.equals(key)) {
                    result = temp.value;
                    break;
                }
            }
        }
        return new int[]{result, count};
    }

    @Override
    public int delete(Integer key) {
        int index = hash(key);
        int count = 0;
        if(bucket.get(index) != null) {
            Entry toDelete = null;
            for(Entry temp : bucket.get(index)) {
                count++;
                if(temp.key.equals(key)) {
                    toDelete = temp;
                    break;
                }
            }
            if(toDelete != null) {
                bucket.get(index).remove(toDelete);
                size--;
                count++;
            }
        }

        return count;
    }


    @Override
    public double getLoadFactor()
    {
        return (double)size / (double)capacity;
    }

}
