package hashingalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

        int numSteps = Integer.parseInt("100");
        int stepSize = Integer.parseInt("50");

        double start, end, avgSet = 0, avgDelete = 0, avgSearch = 0;
        double loadFactor = 0;
        double[] loadFactors = new double[numSteps];
        double[] setCosts = new double[numSteps];
        double[] deleteCosts = new double[numSteps];
        double[] searchCosts = new double[numSteps];

        Random random = new Random();
        IHashingAlgorithm h;
        switch ("1")
        {
            case "1":
                h = new ChainedHashing(1000);
                break;
            case "2":
                h = new LinearProbing(1000);
                break;
            case "3":
                h = new Quadratic(1000);
                break;
            case "4":
                h = new CuckooHashing(1000);
                break;
            default:
                throw new IllegalArgumentException("Illegal function");
        }


        for (int i = 1; i < numSteps; i++) {
            avgSet = 0;
            avgDelete = 0;
            avgSearch = 0;
            loadFactor = 0;
            int range = i * stepSize;

            // generate random keys
            List<Integer> list = new ArrayList<>(range);
            for (int k = 0; k < range; k++) {
                list.add(random.nextInt(1000000));
            }

            start = System.currentTimeMillis();
            for (int k = 0; k < range; k++) {
                h.set(list.get(k), list.get(k));
            }
            end = System.currentTimeMillis();
            avgSet += (end - start);
            loadFactor += h.getLoadFactor();

            Collections.shuffle(list);
            start = System.currentTimeMillis();
            for (int k = 0; k < range; k++) {
                h.search(list.get(k));
            }
            end = System.currentTimeMillis();
            avgSearch += (end - start);

            Collections.shuffle(list);
            start = System.currentTimeMillis();
            for (int k = 0;  k < range; k++) {
                h.delete(list.get(k));
            }
            end = System.currentTimeMillis();
            avgDelete += (end - start);

            loadFactors[i] = loadFactor;
            System.out.println("i = " + i + ", loadFactor: " + loadFactors[i]);

            setCosts[i] = avgSet;
            System.out.println("i = " + i + ", set: " + setCosts[i]);

            deleteCosts[i] = avgDelete;
            System.out.println("i = " + i + ", delete: " + deleteCosts[i]);

            searchCosts[i] = avgSearch;
            System.out.println("i = " + i + ", search: " + searchCosts[i]);

        }

        System.out.println("");

        System.out.println("Load Factors: [");
        for(double i : loadFactors) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        System.out.println("");

        System.out.println("Sets Sequence: [");
        for(double i : setCosts) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        System.out.println("");

        System.out.println("Delete: [");
        for(double i : deleteCosts) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

        System.out.println("");

        System.out.println("Search: [");
        for(double i : searchCosts) {
            System.out.print(i + ", ");
        }
        System.out.println("]");

    }
}
