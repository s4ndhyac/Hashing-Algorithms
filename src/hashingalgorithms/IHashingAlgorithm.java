package hashingalgorithms;

public interface IHashingAlgorithm {

    int hash(Integer key);

    int set(Integer key, Integer value);

    int[] search(Integer key);

    int delete(Integer key);

    double getLoadFactor();

}
