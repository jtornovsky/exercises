package ithelpdesk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CategoryNode {
    Category category;
    int totalPoint;
    CategoryNode(Category category, int totalPoint){
        this.category = category;
        this.totalPoint = totalPoint;
    }
}

class EmployeeNode {
    IEmployee employee;
    int totalPoint;
    EmployeeNode(IEmployee employee, int totalPoint){
        this.employee = employee;
        this.totalPoint = totalPoint;
    }
}

enum Category {
    InformationTechnologies,
    HumanResources,
    Accounting,
    Sales,
    Marketing,
    Legal
}

interface IEmployee {
    void setFullName(String fullName);
    String getFullName();
    void setPointLevel(int pointLevel);
    int getPointLevel();
    void setAssignedCategories(List <Category> assignedCategories);
    List <Category> getAssignedCategories();
}

interface ITicket {
    void setId(int id);
    int getId();
    void setName(String name);
    String getName();
    void setCategory(Category category);
    Category getCategory();
    void setPoint(int point);
    int getPoint();
    void setIsCompleted(boolean isCompleted);
    boolean getIsCompleted();
    void setAssignedEmployee(String assignedEmployee);
    String getAssignedEmployee();
}

interface IHelpDesk {
    void addTicket(ITicket ticket);
    void addEmployee(IEmployee employee);
    void completeTicket(String employeeFullName, int ticketId);
    int getWaitingTicketCount();
    int getCompletedTicketsTotalPoint();
    List <CategoryNode> getTicketsTotalPointByCategory();
    List <EmployeeNode> getTicketsTotalPointByEmployee();
}

/*
 * Create the Employee, Ticket and HelpDesk classes
 */

class Employee implements IEmployee {

    private String fullName;
    private int pointLevel;
    private List<Category> assignedCategories;

    public Employee(String fullName, int pointLevel, List<Category> assignedCategories) {
        this.fullName = fullName;
        this.pointLevel = pointLevel;
        this.assignedCategories = assignedCategories;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getFullName() {
        return fullName;
    }
    public void setPointLevel(int pointLevel) {
        this.pointLevel = pointLevel;
    }
    public int getPointLevel() {
        return pointLevel;
    }
    public void setAssignedCategories(List <Category> assignedCategories) {
        this.assignedCategories = assignedCategories;
    }
    public List <Category> getAssignedCategories() {
        return assignedCategories;
    }
}

class Ticket implements ITicket {

    private int id;
    private String name;
    private Category category;
    private int point;

    private String assignedEmployee;
    private boolean isCompleted;

    public Ticket(int id, String name, Category category, int point) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.point = point;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Category getCategory() {
        return category;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public int getPoint() {
        return point;
    }
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    public boolean getIsCompleted() {
        return isCompleted;
    }
    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }
    public String getAssignedEmployee() {
        return assignedEmployee;
    }
}

class HelpDesk implements IHelpDesk {

    private List<IEmployee> employees;
    private List<ITicket> tickets;

    private int totalCompletedTickets;
    private int totalCompletedTicketsPoints;
    private Map<Category, Integer> categoryMap;
    private Map<IEmployee, Integer> employeeMap;

    public HelpDesk() {
        employees = new ArrayList<>();
        tickets = new ArrayList<>();

        totalCompletedTickets = 0;
        totalCompletedTicketsPoints = 0;
        categoryMap = new HashMap<>();
        employeeMap = new HashMap<>();

        for (Category category : Category.values()) {
            // Add each enum value as a key with an initial value of 0
            categoryMap.put(category, 0);
        }
    }

    public void addTicket(ITicket ticket) {
        tickets.add(ticket);
    }

    public void addEmployee(IEmployee employee) {
        employees.add(employee);
    }

    public void completeTicket(String employeeName, int ticketId) {

        ITicket ticket = findTicketById(ticketId);
        if (ticket == null) {
//            System.out.println("Ticket with id " + ticketId + " not found. Returning");
            return;
        }

        if (ticket.getIsCompleted()) {
//            System.out.println("Ticket with id " + ticketId + " already completed. Returning");
            return;
        }

        IEmployee employee = findEmployeeByName(employeeName);
        if (employee == null) {
//            System.out.println("Employee with name " + employeeName + " not found. Returning");
            return;
        }

        if (employee.getPointLevel() < ticket.getPoint()) {
//            System.out.println("Ticket with id " + ticketId + " not processed by the employee " + employee.getFullName() + ", as employee point level "
//                    + employee.getPointLevel() + " is less than the ticket one " + ticket.getPoint());
            return;
        }

//        System.out.println("Ticket with id " + ticketId + " set to completed.");
        ticket.setIsCompleted(true);
        totalCompletedTickets++;
        totalCompletedTicketsPoints += ticket.getPoint();

        Integer ticketPointValue = categoryMap.get(ticket.getCategory()) + ticket.getPoint();
        categoryMap.put(ticket.getCategory(), ticketPointValue);

        Integer emplValue = employeeMap.getOrDefault(employee, 0) + 1;
        employeeMap.put(employee, emplValue);
    }

    public int getWaitingTicketCount() {
        return tickets.size() - totalCompletedTickets;
    }

    public int getCompletedTicketsTotalPoint() {
        return totalCompletedTicketsPoints;
    }

    public List<CategoryNode> getTicketsTotalPointByCategory() {
        List<CategoryNode> categoryNodes = new ArrayList<>();
        for (Category category : categoryMap.keySet()) {
            CategoryNode categoryNode = new CategoryNode(category, categoryMap.get(category));
            categoryNodes.add(categoryNode);
        }
        return categoryNodes;
    }

    public List<EmployeeNode> getTicketsTotalPointByEmployee() {
        List<EmployeeNode> employeeNodes = new ArrayList<>();

        for (IEmployee employee : employeeMap.keySet()) {
            EmployeeNode employeeNode = new EmployeeNode(employee, employeeMap.get(employee));
            employeeNodes.add(employeeNode);
        }

        return employeeNodes;
    }

    private IEmployee findEmployeeByName(String employeeName) {
        for (IEmployee e : employees) {
            if (e.getFullName().equalsIgnoreCase(employeeName)) {
                return e;
            }
        }
        return null;
    }

    private ITicket findTicketById(int ticketId) {
        for (ITicket t : tickets) {
            if (t.getId() == ticketId) {
                return t;
            }
        }
        return null;
    }
}

public class ItHelpDesk {

    public static void main(String[] args) throws IOException {

        String filePath = "C:\\tmp\\input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter writer = new PrintWriter(System.getenv("OUTPUT_PATH"), "UTF-8");

        String outputPath = "C:\\tmp\\output.txt";

        // Open the file for writing
        PrintWriter writer = new PrintWriter(outputPath, "UTF-8");

        HelpDesk hd = new HelpDesk();

        int eCount = Integer.parseInt(reader.readLine().trim());

        for (int i = 1; i <= eCount; i++) {
            String[] a = reader.readLine().trim().split(",");
            List <Category> eCa = new ArrayList<>();
            for (String cc: a[2].split(" ")) {
                eCa.add(Category.valueOf(cc));
            }
            Employee e = new Employee(a[0], Integer.parseInt(a[1]), eCa);
            hd.addEmployee(e);
        }

        int tCount = Integer.parseInt(reader.readLine().trim());

        for (int i = 1; i <= tCount; i++) {
            String[] a = reader.readLine().trim().split(",");
            Ticket t = new Ticket(Integer.parseInt(a[0]), a[1], Category.valueOf(a[2]), Integer.parseInt(a[3]));
            hd.addTicket(t);
        }

        int pCount = Integer.parseInt(reader.readLine().trim());

        for (int i = 1; i <= pCount; i++) {
            String[] a = reader.readLine().trim().split(",");
            hd.completeTicket(a[0], Integer.parseInt(a[1]));
        }

//        writer.println("WaitingTicketCount:" + hd.getWaitingTicketCount());
//        writer.println("CompletedTicketsTotalPoint:" + hd.getCompletedTicketsTotalPoint());
//        writer.println("TicketsTotalPointByCategory:");

        System.out.println("WaitingTicketCount:" + hd.getWaitingTicketCount());
        System.out.println("CompletedTicketsTotalPoint:" + hd.getCompletedTicketsTotalPoint());
        System.out.println("TicketsTotalPointByCategory:");

        for (CategoryNode item: hd.getTicketsTotalPointByCategory()) {
//            writer.println(item.category.toString() + ":" + item.totalPoint);
            System.out.println(item.category.toString() + ":" + item.totalPoint);
        }

//        writer.println("TicketsTotalPointByEmployee:");
        System.out.println("TicketsTotalPointByEmployee:");

        for (EmployeeNode item: hd.getTicketsTotalPointByEmployee()) {
//            writer.println(item.employee.getFullName() + ":" + item.totalPoint);
            System.out.println(item.employee.getFullName() + ":" + item.totalPoint);
        }

        writer.close();
        reader.close();
    }
}

/*

Input (stdin)
2
John Doe,1,HumanResources InformationTechnologies
Jane Cherry,6,Legal InformationTechnologies
3
1,Ticket1,InformationTechnologies,1
2,Ticket2,HumanResources,5
3,Ticket3,Legal,2
4
John Doe,3
John Doe,1
Jane Cherry,2
Jane Cherry,3

Your Output (stdout)
WaitingTicketCount:0
CompletedTicketsTotalPoint:8
TicketsTotalPointByCategory:
HumanResources:5
Sales:0
InformationTechnologies:1
Legal:2
Accounting:0
Marketing:0
TicketsTotalPointByEmployee:
John Doe:1
Jane Cherry:2


Expected Output
WaitingTicketCount:1
CompletedTicketsTotalPoint:3
TicketsTotalPointByCategory:
InformationTechnologies:1
HumanResources:5
Accounting:0
Sales:0
Marketing:0
Legal:2
TicketsTotalPointByEmployee:
John Doe:1
Jane Cherry:2

 */