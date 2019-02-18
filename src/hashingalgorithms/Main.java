package hashingalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

        int numSteps = Integer.parseInt("100");
        int stepSize = Integer.parseInt("50");
        int avgTime = 100;

        double start, end, avgSet = 0, avgDelete = 0, avgSearch = 0;
        double loadFactor = 0;
        double[] loadFactors = new double[numSteps];
        double[] setCosts = new double[numSteps];
        double[] deleteCosts = new double[numSteps];
        double[] searchCosts = new double[numSteps];

        Random random = new Random();
        IHashingAlgorithm h;

        for (int i = 1; i < numSteps; i++) {
            avgSet = 0;
            avgDelete = 0;
            avgSearch = 0;
            loadFactor = 0;
            for (int j = 0; j < avgTime; j++) {
                switch ("4")
                {
                    case "1":
                        h = new ChainedHashing(100000);
                        break;
                    case "2":
                        h = new LinearProbing(100000);
                        break;
                    case "3":
                        h = new QuadraticProbing(100000);
                        break;
                    case "4":
                        h = new CuckooHashing(5000);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal function");
                }

                // generate random keys
                List<Integer> list = new ArrayList<>(i*stepSize);
                for (int k = 0; k < i*stepSize; k++) {
                    list.add(random.nextInt(1000000));
                }

                start = System.currentTimeMillis();
                for (int k = 0; k < i*stepSize; k++) {
                    h.set(list.get(k), list.get(k));
                }
                end = System.currentTimeMillis();
                avgSet += (end - start);
                loadFactor += h.getLoadFactor();

                Collections.shuffle(list);
                start = System.currentTimeMillis();
                for (int k = 0; k < stepSize && k < i*stepSize; k++) {
                    h.search(list.get(k));
                }
                end = System.currentTimeMillis();
                avgSearch += (end - start);

                Collections.shuffle(list);
                start = System.currentTimeMillis();
                for (int k = 0; k < stepSize && k < i*stepSize; k++) {
                    h.delete(list.get(k));
                }
                end = System.currentTimeMillis();
                avgDelete += (end - start);
            }

            loadFactors[i] = loadFactor/avgTime;
            System.out.println("i = " + i + ", loadFactor: " + loadFactors[i]);

            setCosts[i] = avgSet / avgTime;
            System.out.println("i = " + i + ", set: " + setCosts[i]);

            deleteCosts[i] = avgDelete / avgTime;
            System.out.println("i = " + i + ", delete: " + deleteCosts[i]);

            searchCosts[i] = avgSearch / avgTime;
            System.out.println("i = " + i + ", search: " + searchCosts[i]);

        }

    }
}
