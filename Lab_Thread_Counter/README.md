# Lab_Thread_Counter: Static vs Non-Static Counters in Java Multithreading

**Name:** [Ishtiak Ahmed Ayon]
**ID:** [24039]
**Course:** [ICT 3108]

## Code

File: `Ishtiak_Thread.java`

```java
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
```

Run:

```bash
javac Ishtiak_Thread.java
java Ishtiak_Thread <threads> <increments> <true|false>
```

## Analysis Answers

1. A static variable belongs to the class, with one copy for all objects. A non-static variable belongs to an object, with one copy per object.
2. The static variable is stored once at class level, so every thread accesses the same memory location.
3. Each thread has its own object, and each object has its own instance field. No other thread touches it.
4. `join()` makes the main thread wait for all threads to finish. Without it, the counts would be read before the threads complete.
5. `count++` is read, add, write. Two threads can read the same value and write back the same result, so one increment is lost.
6. No. Loss depends on scheduling, CPU cores, and timing. It usually rises with more threads but not strictly (e.g. 1 thread is always 0%).
7. Thread scheduling is non-deterministic, so the interleaving of updates differs on every run.
8. `AtomicLong` performs the increment as a single atomic operation, so no updates are lost. A plain `long` does not.
9. By Creating one object and pass it to all threads, e.g. `Ishtiak_Thread shared = new Ishtiak_Thread(m, safe);` then `new Thread(shared)` for every thread. Make its counter an `AtomicLong` or use `synchronized`.

