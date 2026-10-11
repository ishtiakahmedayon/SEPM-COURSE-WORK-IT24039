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