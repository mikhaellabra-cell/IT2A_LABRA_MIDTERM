package ui;

import dao.AllocationDao;
import model.Allocation;

import java.util.List;
import java.util.Scanner;

public class DormMenu {

    private static final String LINE = "=".repeat(50);
    private static final String TABLE_LINE = "=".repeat(82);
    private static final String BORDER =
            "+----+--------------------+------------+--------------+----------------+";

    private final Scanner scanner = new Scanner(System.in);
    private final AllocationDao dao = new AllocationDao();

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addAllocation();
                    break;
                case "2":
                    viewAllocations();
                    break;
                case "3":
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter 1, 2 or 3.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println(LINE);
        System.out.println("        STUDENT DORMITORY BED ALLOCATION");
        System.out.println(LINE);
        System.out.println("[1] Add New Allocation");
        System.out.println("[2] View All Allocations");
        System.out.println("[3] Exit System");
        System.out.println(LINE);
        System.out.print("Enter choice: ");
    }

    private void addAllocation() {
        System.out.println("\n--- ADD NEW BED ALLOCATION ---");

        String name = readRequired("Enter Student Name: ");
        String building = readRequired("Enter Building Name: ");
        String room = readRequired("Enter Room Number: ");
        double rent = readRent("Enter Monthly Rent: ");

        Allocation a = new Allocation(0, name, building, room, rent);
        if (dao.insert(a)) {
            System.out.println("\nSUCCESS: Dorm bed allocated successfully!");
        } else {
            System.out.println("\nERROR: Could not save the allocation.");
        }
    }

    private void viewAllocations() {
        List<Allocation> list = dao.findAll();

        System.out.println();
        System.out.println(TABLE_LINE);
        System.out.println("                         DORMITORY BED ALLOCATIONS");
        System.out.println(TABLE_LINE);

        if (list.isEmpty()) {
            System.out.println("No allocation records found.");
            return;
        }

        System.out.println(BORDER);
        System.out.printf("| %-2s | %-18s | %-10s | %-12s | %-14s |%n",
                "ID", "STUDENT NAME", "BUILDING", "ROOM NO", "MONTHLY RENT");
        System.out.println(BORDER);
        for (Allocation a : list) {
            System.out.printf("| %-2d | %-18s | %-10s | %-12s | %-14.2f |%n",
                    a.id(), a.studentName(), a.building(), a.roomNo(), a.monthlyRent());
        }
        System.out.println(BORDER);
        System.out.println("Total Records Displayed: " + list.size());
    }

    private String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    private double readRent(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double rent = Double.parseDouble(scanner.nextLine().trim());
                if (rent >= 0) {
                    return rent;
                }
                System.out.println("Rent cannot be negative. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Enter a number like 3500.00");
            }
        }
    }
}