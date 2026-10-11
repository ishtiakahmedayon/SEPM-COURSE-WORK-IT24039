# When to Stack and When to Heap, Confusing!!
**Name:** [Ishtiak Ahmed Ayon]
**ID:** [24039]
**Course:** [ICT 3108]
## Problem 1: Demonstrate Stack and Heap Memory in Java

File: `Student.java`

```java
import java.util.Scanner;
public class Student{

    int age;

    Student(int age){
        this.age = age;
        System.out.println("Student Object Created");
    }

    public void display(int m){
        int marks;
        marks = m;
        System.out.println("Instance Variable (age): " + age);
        System.out.println("Local Variable (marks): " + marks);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Age: ");
        int age = sc.nextInt();
        System.out.print("Marks: ");
        int marks = sc.nextInt();

        Student s1 = new Student(age);
        System.out.println("Object Identity Hashcode: " + s1.hashCode());
        s1.display(marks);
    }
}
```

## Problem 2: Demonstrating Stack Frames, Heap Objects, and Inner Classes

File: `Employee.java`

```java
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
```