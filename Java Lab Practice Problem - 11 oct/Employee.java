import java.util.Scanner;

public class Employee {
    double salary;


    Employee(double salary){
        this.salary = salary;
        System.out.println("Employee Object Created");
    }

    public void display(double b){
        System.out.println("\nInside the display() method:");
        double bonus;
        bonus = b;
        System.out.println("Salary (Instance Variable): " + salary);
        System.out.println("Bonus (Local Variable): " + bonus);

    }

    class Report{

        Report(){
            System.out.println("\nReport Object Created");
        }

        public void show(){
            System.out.println("Report Identity Hash Code: " + this.hashCode());
            System.out.println("Report: Employee Salary is " + salary);

            System.out.println("\nMemory Concept:");
            System.out.println("Employee object -> Heap");
            System.out.println("Report object -> Heap");
            System.out.println("salary -> Instance field");
            System.out.println("bonus -> Local variable in display()");
            System.out.println("show() -> Executes in its own method frame");
        }
    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();
        System.out.print("Enter Bonus: ");
        double bonus = sc.nextDouble();
        Employee e1 = new Employee(salary);
        System.out.println("Employee Identity Hash Code: " + e1.hashCode());
        e1.display(bonus);

        Employee.Report r1 = e1.new Report();
        r1.show();

    }
}
