package fitnessclub;

import java.io.File;
import java.util.Calendar;
import java.util.Scanner;

public class StudioManager {

    private MemberList memberList;
    private Schedule schedule;

    public StudioManager() {
        memberList = new MemberList();
        schedule = new Schedule();
    }

    public void run() {
        try {
            memberList.load(new File("memberList.txt"));
            schedule.load(new File("classSchedule.txt"));
            System.out.println("Studio Manager is up running...");

            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("Q")) {
                    System.out.println("Studio Manager terminated.");
                    break;
                }
                processCommand(input);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCommand(String command) {
        String[] tokens = command.split("\\s+");
        String cmd = tokens[0].toUpperCase();

        try {
            switch (cmd) {
                case "AB":
                    addBasicMember(tokens);
                    break;
                case "AF":
                    addFamilyMember(tokens);
                    break;
                case "AP":
                    addPremiumMember(tokens);
                    break;
                case "C":
                    cancelMembership(tokens);
                    break;
                case "S":
                    displayClassSchedule();
                    break;
                case "PM":
                    memberList.printByMember();
                    break;
                case "PC":
                    memberList.printByCounty();
                    break;
                case "PF":
                    printMembershipFees();
                    break;
                case "R":
                    registerForMemberClass(tokens);
                    break;
                case "U":
                    unregisterMemberFromClass(tokens);
                    break;
                case "RG":
                    registerGuestForClass(tokens);
                    break;
                case "UG":
                    unregisterGuestFromClass(tokens);
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    private void addBasicMember(String[] tokens) {
        // Add Basic member logic here
    }

    private void addFamilyMember(String[] tokens) {
        // Add Family member logic here
    }

    private void addPremiumMember(String[] tokens) {
        // Add Premium member logic here
    }

    private void cancelMembership(String[] tokens) {
        // Logic to cancel membership
    }

    private void displayClassSchedule() {
        // Logic to display the class schedule
    }

    private void printMembershipFees() {
        // Logic to print membership fees
    }

    private void registerForMemberClass(String[] tokens) {
        // Logic to register a member for a class
    }

    private void unregisterMemberFromClass(String[] tokens) {
        // Logic to unregister a member from a class
    }

    private void registerGuestForClass(String[] tokens) {
        // Logic to register a guest for a class
    }

    private void unregisterGuestFromClass(String[] tokens) {
        // Logic to unregister a guest from a class
    }

}
