import java.util.concurrent.atomic.AtomicLong;

public class Ishtiak_Thread implements Runnable {
    static AtomicLong safeCount = new AtomicLong();
    static long unsafeCount = 0;

    long localCount = 0;
    final long increments;
    final boolean safe;

    Ishtiak_Thread(long increments, boolean safe) {
        this.increments = increments;
        this.safe = safe;
    }

    public void run() {
        for (long i = 0; i < increments; i++) {
            if (safe) safeCount.incrementAndGet();
            else unsafeCount++;
            localCount++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int n = Integer.parseInt(args[0]);
        long m = Long.parseLong(args[1]);
        boolean safe = Boolean.parseBoolean(args[2]);

        Ishtiak_Thread[] tasks = new Ishtiak_Thread[n];
        Thread[] threads = new Thread[n];

        for (int i = 0; i < n; i++) {
            tasks[i] = new Ishtiak_Thread(m, safe);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }
        for (Thread t : threads) t.join();

        long nonStatic = 0;
        for (Ishtiak_Thread t : tasks) nonStatic += t.localCount;

        long staticCount = safe ? safeCount.get() : unsafeCount;
        long diff = Math.abs(staticCount - nonStatic);
        String pct;
        if (nonStatic == 0) pct = staticCount == 0 ? "0.00" : "undefined";
        else pct = String.format("%.4f", diff * 100.0 / nonStatic);

        System.out.println("Threads: " + n);
        System.out.println("Increments/thread: " + m);
        System.out.println("Mode: " + (safe ? "safe" : "unsafe"));
        System.out.println("Expected: " + (n * m));
        System.out.println("Static count: " + staticCount);
        System.out.println("Non-static total: " + nonStatic);
        System.out.println("Absolute difference: " + diff);
        System.out.println("Difference (%): " + pct);
    }
}