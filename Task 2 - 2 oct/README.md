# Task 2 - 2 oct

### Main.java
```
class Student {
    static int count = 0;

    Student() {
        count++;
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        System.out.println(Student.count);
    }
}
```

### ThreadMain.java
-----------

```
public class ThreadMain {
    public static void main(String[] args) {

        CookingTask task1 = new CookingTask("Cooking");
        CookingTask task2 = new CookingTask("Washing");
        CookingTask task3 = new CookingTask("Cleaning");

        task1.start();
        task2.start();
        task3.start();

        System.out.println("All tasks started...");
    }
}

class CookingTask extends Thread {

    private String taskName;

    public CookingTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();

        while (true) {

            System.out.println(
                    Thread.currentThread().getName()
                    + " - Running: " + taskName
            );


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(taskName + " interrupted.");
                break;
            }
            if (System.currentTimeMillis() - startTime >= 10_000) {
                break;
            }
        }

        System.out.println(taskName + " finished.");
    }
}

```

### Output of ThreadMain.java

```
All tasks started...
Thread-2 - Running: Cleaning
Thread-1 - Running: Washing
Thread-0 - Running: Cooking
Thread-0 - Running: Cooking
Thread-2 - Running: Cleaning
Thread-1 - Running: Washing
Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning
Thread-1 - Running: Washing
Thread-0 - Running: Cooking
Thread-2 - Running: Cleaning
Thread-0 - Running: Cooking
Thread-2 - Running: Cleaning
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning
Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-2 - Running: Cleaning
Thread-0 - Running: Cooking
Thread-1 - Running: Washing
Thread-0 - Running: Cooking
Thread-2 - Running: Cleaning
Thread-1 - Running: Washing
Thread-1 - Running: Washing
Thread-0 - Running: Cooking
Thread-2 - Running: Cleaning
Thread-1 - Running: Washing
Thread-0 - Running: Cooking
Thread-2 - Running: Cleaning
Cooking finished.
Cleaning finished.
Washing finished.
```