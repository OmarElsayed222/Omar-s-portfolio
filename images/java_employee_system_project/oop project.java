import javax.naming.Name;
import javax.xml.namespace.QName;

abstract class Person{
    //Attributes
    protected String name;
    protected int age;
    protected int salary;

    public Person() {
    }

    //para onstructor
    public Person( String name ,int age, int salary) {
        this.age = age;
        this.name = name;
        this.salary = salary;
    }
    //Setter and Getter
    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public int getSalary() {

        return salary;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }
    public abstract void displayinfo();
}

class CreditCard{
    private String number;
    private double balance;
    private int exyear;
    private int cvv;

    public CreditCard() {
    }

    public CreditCard(String number, double balance, int exyear, int cvv) {
        this.number = number;
        this.balance = balance;
        this.exyear = exyear;
        this.cvv = cvv;

    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getExyear() {
        return exyear;
    }

    public void setExyear(int exyear) {
        this.exyear = exyear;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
    public void Payment(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Payment of $" + amount + " successful");
            System.out.println("Remaining balance: $" + balance);
        } else {
            System.out.println("Insufficient balance for payment of $" + amount);
        }
    }

    public void addBouns(double amount) {
        balance += amount;
        System.out.println("Added $" + amount + " to the card "+ number);
        System.out.println("New balance: $" + balance);
    }
}
class Doctor extends Person {
    private String specialization;
    private CreditCard creditCard;

    public Doctor(String name, int age, int salary,CreditCard creditCard,String specialization) {
        super(name, age, salary);
        this.specialization = specialization;
        this.creditCard = creditCard;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public void displayinfo() {
        System.out.println(getName() + " is a Doctor specializing in " + specialization);
    }
    public void displayCreditCardInfo() {
        System.out.println("Credit Card Balance for Doctor " + getName() + ": $" + creditCard.getBalance());
    }
}

class Teacher extends Person{
    private String subject;
    private CreditCard creditCard;

    public Teacher(String name, int age, int salary, String subject, CreditCard creditCard) {
        super(name, age, salary);
        this.subject = subject;
        this.creditCard = creditCard;
    }

    @Override
    public void displayinfo() {
        System.out.println(getName() + " is a Teacher Subject in " + subject);
    }
    public void displayCreditCardInfo() {
        System.out.println("Credit Card Balance for Teacher " + getName() + ": $" + creditCard.getBalance());
    }
}
public class Main {
    public static  void main(String[] args) {
        CreditCard doctorCard = new CreditCard("1111-2222-3333-4444", 1000, 2025, 123);
        CreditCard teacherCard = new CreditCard("5555-6666-7777-8888", 500, 2026, 456);
        Doctor doctor = new Doctor("Omar", 45, 120000, doctorCard, "Dentist");
        Teacher teacher = new Teacher("Mohamed",23,40000,"Math",teacherCard);
        doctor.displayinfo();
        System.out.println("Salary: $" + doctor.getSalary());
        teacher.displayinfo();
        System.out.println("Salary: $" + teacher.getSalary());
        doctor.displayCreditCardInfo();
        teacher.displayCreditCardInfo();
        doctorCard.Payment(300);
        teacherCard.addBouns(200);
        teacherCard.Payment(600);
        doctorCard.addBouns(400);
    }
}