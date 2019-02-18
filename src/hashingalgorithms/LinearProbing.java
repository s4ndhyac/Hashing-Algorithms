package hashingalgorithms;

import java.util.ArrayList;

public class LinearProbing implements IHashingAlgorithm {
    private ArrayList<Entry> bucket;
    private int capacity;
    private int size;


    public LinearProbing(int capacity) {
        bucket = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++) {
            bucket.add(null);
        }
        this.capacity = capacity;
        this.size = 0;
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
        while(count != capacity && bucket.get(index) != null && bucket.get(index).key != null && !bucket.get(index).key.equals(key)) {
            index = (index + 1) % capacity;
            count++;
        }
        if(count != capacity || bucket.get(index).key.equals(key)) {
            bucket.set(index, new Entry(key, value));
            size++;
            count++;
        }
        return count;
    }

    @Override
    public int[] search(Integer key) {
        int index = hash(key);
        int count = 0;
        while(count != capacity && bucket.get(index) != null && !bucket.get(index).key.equals(key)) {
            index = (index + 1) % capacity;
            count++;
        }
        int result = -1;
        if(count != capacity && bucket.get(index) != null) {
            result = bucket.get(index).value;
        }
        return new int[]{result, count};
    }

    @Override
    public int delete(Integer key) {
        int index = hash(key);
        int count = 0;
        while(count != capacity && bucket.get(index) != null && (bucket.get(index).key == null || !bucket.get(index).key.equals(key))) {
            index = (index + 1) % capacity;
            count++;
        }

        if(count != capacity && bucket.get(index) != null) {
            bucket.get(index).key = null;
            size--;
            count++;
        }
        return count;
    }

    @Override
    public double getLoadFactor()
    {
        return (double)size / (double)capacity;
    }

}
