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
