# Task 1 - 28 Sept

### MainCount.java

```

class Count {
    static int count = 0;

    Count() {
        count++;
        System.out.println(count);
    }
}

public class MainCount {
    public static void main(String[] args) {
        Count c1 = new Count();
        Count c2 = new Count();
        Count c3 = new Count();

        System.out.println("Final count: " + Count.count);
    }
}
```
### Main.java (Cooking Class)
```
class CookingTask extends Thread {
    public void run() {
        System.out.println("This is a cooking task.");
    }
}

public class Main {
    public static void main(String[] args) {
        CookingTask t1 = new CookingTask();
        CookingTask t2 = new CookingTask();
        CookingTask t3 = new CookingTask();
        t1.start();
        t2.start();
        t3.start();
    }
}

```