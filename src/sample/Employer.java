package sample;

/**
 * @author - Sivaprakash Nithyanandam Oct 07,2025 - 2:40 PM
 */

public class Employer {

    private int id;
    private String name;
    private String address;
    private int salary;
    private boolean isLeave;

    public boolean doWork(){
        System.out.println("I am working");
        System.out.println("Work completed");
        return true;
    }

    public void signIn(){
        System.out.println("I am Signing in");
    }

    public void signOut(){
        System.out.println("I am signing out");
    }

    public boolean askLeave(){
        System.out.println("Leave permitted");
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int givenId) {
        id = givenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public boolean isLeave() {
        return isLeave;
    }

    public void setLeave(boolean leave) {
        isLeave = leave;
    }
}
