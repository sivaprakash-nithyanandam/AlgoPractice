package sample;

/**
 * @author - Sivaprakash Nithyanandam Oct 07,2025 - 2:12 PM
 */

public class Worker {

    private int id;
    private String name;
    private String address;
    private String department;
    private int salary;
    private boolean isLeave;

    public void setId(int givenId) {
        id = givenId;
    }

    public int getId() {
        return id;
    }

    public void setName(String givenName) {
        name=givenName;
    }

    public String getName(){
        return name;
    }

    public void setAddress(String givenAddress) {
        address=givenAddress;
    }

    public String getAddress(){
        return address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

}
