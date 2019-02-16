package hashingalgorithms;

public class Timer {
    long startTime, endTime, elapsedTime, memAvailable, memUsed;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void scale(int num) {
        elapsedTime /= num;
    }

    public Timer end() {
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        memAvailable = Runtime.getRuntime().totalMemory();
        memUsed = memAvailable - Runtime.getRuntime().freeMemory();
        return this;
    }

    @Override
    public String toString() {
        return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) +
                " MB / " + (memAvailable / 1048576) + " MB.";
    }
}
