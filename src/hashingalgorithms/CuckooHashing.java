package hashingalgorithms;

public class CuckooHashing implements IHashingAlgorithm {

    private Entry[] hashtable1;
    private Entry[] hashtable2;
    private int a = 37452, b = 43807;
    int size;
    int capacity;

    public CuckooHashing() {
        hashtable1 = new Entry[16];
        hashtable2 = new Entry[16];
        capacity = 16;
        size = 0;
    }

    public CuckooHashing(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        hashtable1 = new Entry[initialCapacity];
        hashtable2 = new Entry[initialCapacity];
        capacity = initialCapacity;
        size = 0;
    }

    @Override
    public int[] search(Integer key) {
        int index = indexFor(hash0(key), capacity);
        if(hashtable1[index].key == key)
            return new int[]{hashtable1[index].value, 1};

        index = indexFor(hash1(key), capacity);
        if(hashtable2[index].key == key)
            return new int[] {hashtable2[index].value, 1};

        return new int[] {-1, 1};
    }

    @Override
    public int set(Integer key, Integer value) {
        if(hashtable1[indexFor(hash0(key), capacity)] != null && hashtable1[indexFor(hash0(key), capacity)].key == key) {
            hashtable1[indexFor(hash0(key), capacity)].value = value;
            return 1;
        }

        if(hashtable2[indexFor(hash1(key), capacity)] != null && hashtable2[indexFor(hash1(key), capacity)].key == key) {
            hashtable2[indexFor(hash1(key), capacity)].value = value;
            return 1;
        }

        int t = 0, counter = 0, index = 0;
        Entry entry = new Entry(key, value);
        Entry tmp;

        index = indexFor(hash0(entry.key), capacity);
        tmp = hashtable1[index];
        hashtable1[index] = entry;
        entry = tmp;
        t = 1;
        counter++;

        while(entry != null && counter <= (size+1)) {
            if(t == 0) {
                index = indexFor(hash0(entry.key), capacity);
                tmp = hashtable1[index];
                hashtable1[index] = entry;
                entry = tmp;
            } else {
                index = indexFor(hash1(entry.key), capacity);
                tmp = hashtable2[index];
                hashtable2[index] = entry;
                entry = tmp;
            }
            t = 1 - t;
            counter++;
        }

        if(counter > (size+1)) {
            rehash();
            set(entry.key, entry.value);
        }
        size++;
        return 1;
    }

    @Override
    public int delete(Integer key) {
        int index1 = indexFor(hash0(key), capacity);
        int index2 = indexFor(hash1(key), capacity);
        if(hashtable1[index1] != null && hashtable1[index1].key == key) {
            hashtable1[index1] = null;
            size--;
        } else if(hashtable2[index2] != null && hashtable2[index2].key == key) {
            hashtable2[index2] = null;
            size--;
        } else
            return -1;
        return 1;
    }

    private void rehash() {
        Entry[] oldHashtable1 = hashtable1;
        Entry[] oldHashtable2 = hashtable2;

        if(size*2 >= capacity) {
            capacity *= 2;
        }
        size = 0;
        hashtable1 = new Entry[capacity];
        hashtable2 = new Entry[capacity];

        for(Entry e : oldHashtable1){
            if(e == null)
                continue;
            set(e.key, e.value);
        }
        for(Entry e : oldHashtable2){
            if(e == null)
                continue;
            set(e.key, e.value);
        }
    }

    @Override
    public double getLoadFactor() {
        return (double)size / (double)capacity;
    }

    int hash0(Integer h) {
        int result = 2135423121;
        String s = h + "";
        for (int i = 0; i < s.length(); i++) {
            result ^= (result << 5) +(s.charAt(i) - 'a' + a + b) + (result >> 2);
        }
        return result;
    }

    int hash1(Integer h) {
        int result = 0, c = a;
        String s = h + "";
        for (int i = 0; i < s.length(); i++) {
            result = result * c + (s.charAt(i) - 'a');
            c *= b;
        }
        return result;
    }

    @Override
    public int hash(Integer key)
    {
        throw new IllegalArgumentException("Not Implemented");
    }

    static int indexFor(Integer hashValue, Integer capacity) {
        return Math.abs(hashValue) % capacity;
    }



}
