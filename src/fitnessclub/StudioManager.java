package fitnessclub;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.NoSuchElementException;
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
            try {
                memberList.load(new File("memberList.txt"));

                schedule.load(new File("classSchedule.txt"));
            } catch (NoSuchElementException e) {
                return;
            }
            catch (NumberFormatException e) {
                return;
            }
            catch (FileNotFoundException e) {
                return;
            }

            System.out.println("Studio Manager is up running...");

            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) {
                input = scanner.nextLine();
                while (input.isEmpty()) {
                    input = scanner.nextLine();
                }
                if (input.equals("Q")) {
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
        String[] tokens = command.split(" ");
        String cmd = tokens[0];

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
                    System.out.println(cmd + " is an invalid command!");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    private void addBasicMember(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        String dobStr = tokens[3];
        String studioStr = tokens[4].toUpperCase();

        Date dob = new Date(dobStr);
        if (!dob.isValid()) {
            System.out.println("DOB " + dobStr + ": invalid calendar date!");
            return;
        }

        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dobStr + ": cannot be today or a future date!");
            return;
        }

        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dobStr + ": must be 18 or older to join!");
            return;
        }

        Location studio;
        try {
            studio = Location.valueOf(studioStr);
        } catch (IllegalArgumentException e) {
            System.out.println(studioStr + ": invalid studio location!");
            return;
        }

        Profile newProfile = new Profile(firstName, lastName, dob);

        if (memberList.sameProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }

        // Assuming the current date is used to set the membership expiration date to one month later
        Date expireDate = dob.plusMonths(1); // Ensure the Date class has a method to add months to a date
        Member newMember = new Basic(newProfile, expireDate, studio, 0); // Assuming 0 classes attended initially
        memberList.add(newMember);
        System.out.println("Basic member added.");
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
