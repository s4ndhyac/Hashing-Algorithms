package hashingalgorithms;

import java.util.ArrayList;

public class LinearProbing implements IHashingAlgorithm {
    private ArrayList<Entry> list;
    private int capacity;
    private int size;


    public LinearProbing(int capacity) {
        list = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++) {
            list.add(null);
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
        while(count != capacity && list.get(index) != null && list.get(index).key != null && !list.get(index).key.equals(key)) {
            index = (index + 1) % capacity;
            count++;
        }
        if(count != capacity || list.get(index).key.equals(key)) {
            list.set(index, new Entry(key, value));
            size++;
            count++;
        }
        return count;
    }

    @Override
    public int[] search(Integer key) {
        int index = hash(key);
        int count = 0;
        while(count != capacity && list.get(index) != null && !list.get(index).key.equals(key)) {
            index = (index + 1) % capacity;
            count++;
        }
        int result = -1;
        if(count != capacity && list.get(index) != null) {
            result = list.get(index).value;
        }
        return new int[]{result, count};
    }

    @Override
    public int delete(Integer key) {
        int index = hash(key);
        int count = 0;
        while(count != capacity && list.get(index) != null && (list.get(index).key == null || !list.get(index).key.equals(key))) {
            index = (index + 1) % capacity;
            count++;
        }

        if(count != capacity && list.get(index) != null) {
            list.get(index).key = null;
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
