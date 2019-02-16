package hashingalgorithms;

import java.util.ArrayList;

public class Quadratic implements IHashingAlgorithm{

    private ArrayList<Entry> list;
    private int capacity;
    private int size;


    public Quadratic(int capacity) {
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
        int count = 0;
        int hashVal = hash(key);
        int index = (hashVal + count * count) % capacity;
        while(count < capacity && list.get(index) != null && list.get(index).key != null && !list.get(index).key.equals(key)) {
            index = (hashVal + count * count) % capacity;
            count++;
        }
        if(count < capacity|| list.get(index).key.equals(key)) {
            list.set(index, new Entry(key, value));
            count++;
            size++;
        }
        return count;
    }


    @Override
    public int[] search(Integer key) {
        int count = 0;
        int hashVal = hash(key);
        int index = (hashVal + count * count) % capacity;
        while(count < capacity && list.get(index) != null && (list.get(index).key == null || !list.get(index).key.equals(key))) {
            index = (hashVal + count * count) % capacity;
            count++;
        }
        int result = -1;
        if(count < capacity && list.get(index) != null) {
            result = list.get(index).value;
        }
        return new int[]{result, count};
    }

    
    @Override
    public int delete(Integer key) {
        int count = 0;
        int hashVal = hash(key);
        int index = (hashVal + count * count) % capacity;
        while(count < capacity && list.get(index) != null && (list.get(index).key == null || !list.get(index).key.equals(key))) {
            index = (hashVal + count * count) % capacity;
            count++;
        }

        if(count < capacity && list.get(index) != null) {
            list.get(index).key = null;
            count++;
            size--;
        }
        return count;
    }

    @Override
    public double getLoadFactor()
    {
        return (double)size / (double)capacity;
    }

}
