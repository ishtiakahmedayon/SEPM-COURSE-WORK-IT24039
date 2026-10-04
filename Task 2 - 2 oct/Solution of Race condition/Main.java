//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException{
        Thread1 t1 = new Thread1();
        Thread1 t2 = new Thread1();
        Thread1 t3 = new Thread1();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Expected count = " + 3 * 100000);
        System.out.println("Actual count = " + Thread1.count);

        Thread2 t4 = new Thread2();
        Thread2 t5 = new Thread2();
        Thread2 t6 = new Thread2();

        t4.start();
        t5.start();
        t6.start();

        t4.join();
        t5.join();
        t6.join();

        System.out.println("Expected count = " + 3 * 100000);
        System.out.println("Actual count = " + Thread2.count.get());
    }
}