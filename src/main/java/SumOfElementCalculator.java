public class SumOfElementCalculator implements Runnable{
    private long sum;
    private int[] arrayOfInt;

    public SumOfElementCalculator(int[] arrayOfInt) {
        this.arrayOfInt = arrayOfInt;
    }

    public long getSum() {
        return sum;
    }

    @Override
    public void run() {
        sum = TaskManager.usualCalculateSumOfArrayElement(arrayOfInt);
    }
}
