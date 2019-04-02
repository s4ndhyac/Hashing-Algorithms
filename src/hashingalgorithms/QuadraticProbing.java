import java.util.ArrayList;

public class QuadraticProbing implements IHashingAlgorithm{

    private ArrayList<Entry> bucket;
    private int capacity;
    private int size;


    public QuadraticProbing(int capacity) {
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
        int count = 0;
        int hashVal = hash(key);
        int index = (hashVal + count * count) % capacity;
        while(count < capacity && bucket.get(index) != null && bucket.get(index).key != null && !bucket.get(index).key.equals(key)) {
            index = (hashVal + count * count) % capacity;
            count++;
        }
        if(count < capacity|| bucket.get(index).key.equals(key)) {
            bucket.set(index, new Entry(key, value));
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
        while(count < capacity && bucket.get(index) != null && (bucket.get(index).key == null || !bucket.get(index).key.equals(key))) {
            index = (hashVal + count * count) % capacity;
            count++;
        }
        int result = -1;
        if(count < capacity && bucket.get(index) != null) {
            result = bucket.get(index).value;
        }
        return new int[]{result, count};
    }

    
    @Override
    public int delete(Integer key) {
        int count = 0;
        int hashVal = hash(key);
        int index = (hashVal + count * count) % capacity;
        while(count < capacity && bucket.get(index) != null && (bucket.get(index).key == null || !bucket.get(index).key.equals(key))) {
            index = (hashVal + count * count) % capacity;
            count++;
        }

        if(count < capacity && bucket.get(index) != null) {
            bucket.get(index).key = null;
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
