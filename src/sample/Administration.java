package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author - Sivaprakash Nithyanandam Oct 07,2025 - 2:49 PM
 */

public class Administration {

    public static void main(String[] args) {
        Administration o = new Administration();
        System.out.println();

        Employer employer = new Employer();
        employer.setId(1);
        employer.setName("John");
        employer.setAddress("California");
        employer.setSalary(168000);

        ArrayList<Worker> workers = new ArrayList<>();

        Worker worker1 = new Worker();
        worker1.setId(2);
        worker1.setSalary(10);
        workers.add(worker1);

        Worker worker2 = new Worker();
        worker2.setSalary(20);
        worker2.setId(3);
        workers.add(worker2);

        Worker worker3 = new Worker();
        worker3.setId(4);
        worker3.setSalary(40);
        worker3.setDepartment("Kitchen");
        workers.add(worker3);

        ArrayList<Integer> register = new ArrayList<>();
        register.add(3);
        register.add(4);

        Map<Integer, Integer> idSalaryMap = new HashMap<>();

        for (Worker worker : workers) {
            idSalaryMap.put(worker.getId(), worker.getSalary());
        }

        int totalSalary = 0;

        for(Integer id : register) {
            int daySalary = idSalaryMap.get(id);
            totalSalary = totalSalary + daySalary;
        }

        System.out.println("Today's workers salary : " + totalSalary);
    }

}