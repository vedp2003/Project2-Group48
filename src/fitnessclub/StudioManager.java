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
            System.out.println("The date contains characters.");
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
        String studioStr = tokens[4];

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
            studio = Location.valueOf(studioStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioStr + ": invalid studio location!");
            return;
        }

        Profile newProfile = new Profile(firstName, lastName, dob);

        if (memberList.sameProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }

        Date expireDate = dob.plusMonths(1);
        Member newMember = new Basic(newProfile, expireDate, studio, 0);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    private void addFamilyMember(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        String dobStr = tokens[3];
        String studioStr = tokens[4];

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
            studio = Location.valueOf(studioStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioStr + ": invalid studio location!");
            return;
        }

        Profile newProfile = new Profile(firstName, lastName, dob);

        if (memberList.sameProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }

        Date expireDate = dob.plusMonths(3);
        Member newMember = new Family(newProfile, expireDate, studio, true);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");

    }

    private void addPremiumMember(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        String dobStr = tokens[3];
        String studioStr = tokens[4];

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
            studio = Location.valueOf(studioStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioStr + ": invalid studio location!");
            return;
        }

        Profile newProfile = new Profile(firstName, lastName, dob);

        if (memberList.sameProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }

        Date expireDate = dob.plusYears(1);
        Member newMember = new Premium(newProfile, expireDate, studio, 3);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    private void cancelMembership(String[] tokens) {
        if (tokens.length != 4) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        Date dob = new Date(tokens[3]);

        //Profile newProfile = new Profile(firstName, lastName, dob);

        //if (memberList.sameProfile(newProfile)) {

        // Iterate over the member list to find and remove the member.
        boolean foundAndRemoved = false;

        for (Member member : memberList.getMembers()) {
            if(member == null) {
                foundAndRemoved = false;
                break;
            }
            if (member.getProfile().getFname().equalsIgnoreCase(firstName) && member.getProfile().getLname().equalsIgnoreCase(lastName)
            && member.getProfile().getDob().equals(dob)) {
                foundAndRemoved = memberList.remove(member);
                break;
            }
        }

        if (foundAndRemoved) {
            System.out.println(firstName + " " + lastName + " removed.");
        } else {
            System.out.println(firstName + " " + lastName + " is not in the member database.");
        }


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
