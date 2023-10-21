import java.util.Arrays;

public class TaskManager {
    private static int quantityOfTask = 100;
    private static int arraySize = 10000000;
    private static int[] arrayOfInt;

    public static void factorialCalculatorInDifferentThreads() {
        FactorialTask[] factorialTasks = TaskManager.getTaskPool(quantityOfTask);
        Thread[] threadsPool = TaskManager.getThreadPool(factorialTasks);
        TaskManager.startAndJoinThreads(threadsPool);
    }

    private static FactorialTask[] getTaskPool(int quantityOfTask) {
        FactorialTask[] factorialTasks = new FactorialTask[quantityOfTask];
        for (int i = 0; i < quantityOfTask; i++) {
            factorialTasks[i] = new FactorialTask();
        }
        return factorialTasks;
    }

    private static Thread[] getThreadPool(Runnable[] tasks) {
        Thread[] threadsPool = new Thread[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            threadsPool[i] = new Thread(tasks[i]);
        }
        return threadsPool;
    }

    private static void startAndJoinThreads(Thread[] threadsPool) {
        for (int i = 0; i < threadsPool.length; i++) {
            threadsPool[i].start();
        }
        for (int i = 0; i < threadsPool.length; i++) {
            try {
                threadsPool[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void countingSumOfArrayElements() {
        arrayOfInt = getArrayOfInt(arraySize);
        long startUsualCalculateSumOfArrayElement = System.nanoTime();
        long usualMethodSum = usualCalculateSumOfArrayElement(arrayOfInt);
        long finishUsualCalculateSumOfArrayElement = System.nanoTime();
        System.out.println("Usual calculation. Sum = " + usualMethodSum + " it lasted : " + (finishUsualCalculateSumOfArrayElement - startUsualCalculateSumOfArrayElement));
        calculateSumOfArrayElementInThreads(arrayOfInt);


    }

    private static int[] getArrayOfInt(int arraySize) {
        arrayOfInt = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            arrayOfInt[i] = (int) (Math.random() * 100);
        }
        return arrayOfInt;
    }

    public static long usualCalculateSumOfArrayElement(int[] arrayOfInt) {
        long sum = 0;
        for (int element : arrayOfInt) {
            sum += element;
        }
        return sum;
    }

    private static void calculateSumOfArrayElementInThreads(int[] arrayOfInt) {
        long sum = 0;
        SumOfElementCalculator[] tasks = new SumOfElementCalculator[4];
        tasks[0] = new SumOfElementCalculator(Arrays.copyOfRange(arrayOfInt, 0, 2500000));
        tasks[1] = new SumOfElementCalculator(Arrays.copyOfRange(arrayOfInt, 2500000, 5000000));
        tasks[2] = new SumOfElementCalculator(Arrays.copyOfRange(arrayOfInt, 5000000, 7500000));
        tasks[3] = new SumOfElementCalculator(Arrays.copyOfRange(arrayOfInt, 7500000, 10000000));
        Thread[] threadsPool = getThreadPool(tasks);
        long startCalculateSumOfArrayElement = System.nanoTime();
        startAndJoinThreads(threadsPool);
        long finishCalculateSumOfArrayElement = System.nanoTime();
        for (int i = 0; i < tasks.length; i++) {
            sum += tasks[i].getSum();
        }
        System.out.println("Thread calculation. Sum = " + sum + " it lasted : " + (finishCalculateSumOfArrayElement - startCalculateSumOfArrayElement));
    }
}
