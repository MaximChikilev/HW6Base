import java.math.BigInteger;

public class FactorialTask implements Runnable {
    private BigInteger result = BigInteger.ZERO;

    public BigInteger getResult() {
        return result;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        BigInteger fact = factorialCalculate(thread.threadId());
        System.out.println("Current taskId : "+thread.threadId()+", calculation result : "+fact);
        result = result.add(fact);
    }

    public BigInteger factorialCalculate(long n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }
}
