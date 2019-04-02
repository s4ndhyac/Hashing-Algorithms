import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

        int dataPoints = Integer.parseInt(args[0]);
        int intervalSize = Integer.parseInt(args[1]);
        int numTrials = Integer.parseInt(args[2]);
        int arraySize = dataPoints * intervalSize;

        double start, end, setAvg = 0, deleteAvg = 0, searchAvg = 0;
        double loadFactor = 0;
        double[] loadFactors = new double[dataPoints];
        double[] setRT = new double[dataPoints];
        double[] deleteRT = new double[dataPoints];
        double[] searchRT = new double[dataPoints];

        Random random = new Random();
        IHashingAlgorithm h;

        for (int i = 1; i < dataPoints; i++) {
            setAvg = 0;
            deleteAvg = 0;
            searchAvg = 0;
            loadFactor = 0;
            int range = i * intervalSize;
            for (int j = 0; j < numTrials; j++) {
                switch (args[3])
                {
                    case "1":
                        h = new ChainedHashing(arraySize);
                        break;
                    case "2":
                        h = new LinearProbing(arraySize);
                        break;
                    case "3":
                        h = new QuadraticProbing(arraySize);
                        break;
                    case "4":
                        h = new CuckooHashing(arraySize);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal function");
                }

                // generate random keys
                List<Integer> list = new ArrayList<>(i*intervalSize);
                for (int k = 0; k < range; k++) {
                    list.add(random.nextInt(1000000));
                }

                start = System.currentTimeMillis();
                for (int k = 0; k < range; k++) {
                    h.set(list.get(k), list.get(k));
                }
                end = System.currentTimeMillis();
                setAvg += (end - start);
                loadFactor += h.getLoadFactor();

                Collections.shuffle(list);
                start = System.currentTimeMillis();
                for (int k = 0; k < range; k++) {
                    h.search(list.get(k));
                }
                end = System.currentTimeMillis();
                searchAvg += (end - start);

                Collections.shuffle(list);
                start = System.currentTimeMillis();
                for (int k = 0; k < range; k++) {
                    h.delete(list.get(k));
                }
                end = System.currentTimeMillis();
                deleteAvg += (end - start);
            }

            loadFactors[i] = loadFactor/numTrials;
            System.out.println("i = " + i + ", loadFactor: " + loadFactors[i]);

            setRT[i] = setAvg / numTrials;
            System.out.println("i = " + i + ", set: " + setRT[i]);

            deleteRT[i] = deleteAvg / numTrials;
            System.out.println("i = " + i + ", delete: " + deleteRT[i]);

            searchRT[i] = searchAvg / numTrials;
            System.out.println("i = " + i + ", search: " + searchRT[i]);

        }

    }
}
