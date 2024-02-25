package fitnessclub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the user interface class that processes the input and output of data
 *
 * @author Ved Patel, Vivek Manthri
 */
public class StudioManager {
    public static final int CMD_NAME_INDEX = 0;
    public static final int ADD_MEMBERSHIP_INPUT_MAX = 5;
    public static final int CANCEL_MEMBERSHIP_INPUT_MAX = 4;
    public static final int ADD_REMOVE_CLASS_INPUT_MAX = 7;
    public static final int ADD_CANCEL_MEMBERSHIP_FIRST_NAME_INDEX = 1;
    public static final int ADD_CANCEL_MEMBERSHIP_LAST_NAME_INDEX = 2;
    public static final int ADD_CANCEL_MEMBERSHIP_DOB_INDEX = 3;
    public static final int ADD_MEMBERSHIP_STUDIO_INDEX = 4;
    public static final int BASIC_EXPIRY_MONTH_LIMIT_INDEX = 1;
    public static final int BASIC_INITIAL_CLASSES = 0;
    public static final int FAMILY_EXPIRY_MONTH_LIMIT_INDEX = 3;
    public static final int PREMIUM_EXPIRY_YEAR_LIMIT_INDEX = 1;
    public static final int PREMIUM_GUEST_PASS_LIMIT = 3;
    public static final int MEMBER_GUEST_CLASS_TYPE_INDEX = 1;
    public static final int MEMBER_GUEST_CLASS_INSTRUCTOR_INDEX = 2;
    public static final int MEMBER_GUEST_CLASS_STUDIO_INDEX = 3;
    public static final int MEMBER_GUEST_FIRST_NAME_INDEX = 4;
    public static final int MEMBER_GUEST_LAST_NAME_INDEX = 5;
    public static final int MEMBER_GUEST_DOB_INDEX = 6;

    private MemberList memberList;
    private Schedule schedule;

    /**
     * Default constructor/no-argument constructor
     */
    public StudioManager() {
        memberList = new MemberList();
        schedule = new Schedule();
    }

    /**
     * Loads the text files, runs the Studio Manager, takes in input commands,
     * and calls the appropriate processing method
     */
    public void run() {
        try {
            memberList = new MemberList();
            memberList.load(new File("memberList.txt"));
            System.out.println("-list of members loaded-");
            printMembers();
            System.out.println("-end of list-\n\n");

            schedule = new Schedule();
            schedule.load(new File("classSchedule.txt"));
            System.out.println("-Fitness classes loaded-");
            printClasses();
            System.out.println("-end of class list.\n");

        } catch (IOException e) {
            return;
        }

        System.out.println("Studio Manager is up running...\n");
        Scanner scanner = new Scanner(System.in);
        String inputStr;
        while (true) {
            inputStr = scanner.nextLine();
            while (inputStr.isEmpty()) {
                inputStr = scanner.nextLine();
            }
            if (inputStr.equals("Q")) {
                System.out.println("Studio Manager terminated.");
                break;
            }
            processInputs(inputStr);
        }
        scanner.close();
    }

    /**
     * Helper method to process the input command and delegate to the respective methods
     *
     * @param input the string representing the command terminal input
     */
    private void processInputs(String input) {
        String[] strSplit = input.split("\\s");
        String commandName = strSplit[CMD_NAME_INDEX];
        if (strSplit.length != ADD_MEMBERSHIP_INPUT_MAX && (commandName.equals("AB") ||
                commandName.equals("AF") || commandName.equals("AP"))) {
            System.out.println("Missing data tokens.");
            return;
        }
        if (commandName.equals("AB")) {
            addBasicMember(strSplit);
        } else if (commandName.equals("AF")) {
            addFamilyMember(strSplit);
        } else if (commandName.equals("AP")) {
            addPremiumMember(strSplit);
        } else if (commandName.equals("C")) {
            cancelMembership(strSplit);
        } else if (commandName.equals("S")) {
            displayClassSchedule();
        } else if (commandName.equals("PM")) {
            memberList.printByMember();
        } else if (commandName.equals("PC")) {
            memberList.printByCounty();
        } else if (commandName.equals("PF")) {
            memberList.printFees();
        } else if (commandName.equals("R")) {
            memberClassAttendance(strSplit);
        } else if (commandName.equals("U")) {
            removeMemberFromClass(strSplit);
        } else if (commandName.equals("RG")) {
            guestClassAttendance(strSplit);
        } else if (commandName.equals("UG")) {
            removeGuestFromClass(strSplit);
        } else {
            System.out.println(commandName + " is an invalid command!");
        }
    }

    /**
     * Helper method to print the members from the list of members
     */
    private void printMembers() {
        for (int i = 0; i < memberList.getSize(); i++) {
            System.out.println(memberList.getMembers()[i]);
        }
    }

    /**
     * Helper method to print the classes from the schedule's list of fitness classes
     */
    private void printClasses() {
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            System.out.println(schedule.getClasses()[i]);
        }
    }

    /**
     * Adds a member with Basic membership to the list of members based on the provided input tokens.
     * Ensures all necessary condition checks before adding a member to the list of members
     *
     * @param parts an array of strings, where each element represents a specific piece of information
     *              from the command line argument
     */
    private void addBasicMember(String[] parts) {
        String firstName = parts[ADD_CANCEL_MEMBERSHIP_FIRST_NAME_INDEX];
        String lastName = parts[ADD_CANCEL_MEMBERSHIP_LAST_NAME_INDEX];
        Date dob;
        try {
            dob = new Date(parts[ADD_CANCEL_MEMBERSHIP_DOB_INDEX]);
        } catch (NumberFormatException e) {
            System.out.println("The date contains characters.");
            return;
        }
        String studioLocationString = parts[ADD_MEMBERSHIP_STUDIO_INDEX];
        if (!dob.isValid()) {
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return;
        }
        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return;
        }
        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return;
        }
        Location studioLocation;
        try {
            studioLocation = Location.valueOf(studioLocationString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioLocationString + ": invalid studio location!");
            return;
        }
        Profile newProfile = new Profile(firstName, lastName, dob);
        if (memberList.containsProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }
        Date expireDate = new Date().plusMonths(BASIC_EXPIRY_MONTH_LIMIT_INDEX);
        Member newMember = new Basic(newProfile, expireDate, studioLocation, BASIC_INITIAL_CLASSES);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    /**
     * Adds a member with Family membership to the list of members based on the provided input tokens.
     * Ensures all necessary condition checks before adding a member to the list of members
     *
     * @param parts an array of strings, where each element represents a specific piece of information
     *              from the command line argument
     */
    private void addFamilyMember(String[] parts) {
        String firstName = parts[ADD_CANCEL_MEMBERSHIP_FIRST_NAME_INDEX];
        String lastName = parts[ADD_CANCEL_MEMBERSHIP_LAST_NAME_INDEX];
        Date dob;
        try {
            dob = new Date(parts[ADD_CANCEL_MEMBERSHIP_DOB_INDEX]);
        } catch (NumberFormatException e) {
            System.out.println("The date contains characters.");
            return;
        }
        String studioLocationString = parts[ADD_MEMBERSHIP_STUDIO_INDEX];
        if (!dob.isValid()) {
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return;
        }
        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return;
        }
        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return;
        }
        Location studioLocation;
        try {
            studioLocation = Location.valueOf(studioLocationString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioLocationString + ": invalid studio location!");
            return;
        }
        Profile newProfile = new Profile(firstName, lastName, dob);
        if (memberList.containsProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }
        Date expireDate = new Date().plusMonths(FAMILY_EXPIRY_MONTH_LIMIT_INDEX);
        Member newMember = new Family(newProfile, expireDate, studioLocation, true);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    /**
     * Adds a member with Premium membership to the list of members based on the provided input tokens.
     * Ensures all necessary condition checks before adding a member to the list of members
     *
     * @param parts an array of strings, where each element represents a specific piece of information
     *              from the command line argument
     */
    private void addPremiumMember(String[] parts) {
        String firstName = parts[ADD_CANCEL_MEMBERSHIP_FIRST_NAME_INDEX];
        String lastName = parts[ADD_CANCEL_MEMBERSHIP_LAST_NAME_INDEX];
        Date dob;
        try {
            dob = new Date(parts[ADD_CANCEL_MEMBERSHIP_DOB_INDEX]);
        } catch (NumberFormatException e) {
            System.out.println("The date contains characters.");
            return;
        }
        String studioLocationString = parts[ADD_MEMBERSHIP_STUDIO_INDEX];
        if (!dob.isValid()) {
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return;
        }
        if (dob.isTodayOrFutureDate()) {
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return;
        }
        if (dob.isLessThan18(dob)) {
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return;
        }
        Location studioLocation;
        try {
            studioLocation = Location.valueOf(studioLocationString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioLocationString + ": invalid studio location!");
            return;
        }
        Profile newProfile = new Profile(firstName, lastName, dob);
        if (memberList.containsProfile(newProfile)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }
        Date expireDate = new Date().plusYears(PREMIUM_EXPIRY_YEAR_LIMIT_INDEX);
        Member newMember = new Premium(newProfile, expireDate, studioLocation, PREMIUM_GUEST_PASS_LIMIT);
        memberList.add(newMember);
        System.out.println(firstName + " " + lastName + " added.");
    }

    /**
     * Cancels the membership and removes the member from the member database.
     * Ensures necessary condition checks before removing a member from the list of members
     *
     * @param parts an array of strings, where each element represents a specific piece of information
     *              from the command line argument
     */
    private void cancelMembership(String[] parts) {
        if (parts.length != CANCEL_MEMBERSHIP_INPUT_MAX) {
            System.out.println("Missing data tokens.");
            return;
        }
        String firstName = parts[ADD_CANCEL_MEMBERSHIP_FIRST_NAME_INDEX];
        String lastName = parts[ADD_CANCEL_MEMBERSHIP_LAST_NAME_INDEX];
        Date dob;
        try {
            dob = new Date(parts[ADD_CANCEL_MEMBERSHIP_DOB_INDEX]);
        } catch (NumberFormatException e) {
            System.out.println("The date contains characters.");
            return;
        }
        Profile cancelProfile = new Profile(firstName, lastName, dob);

        boolean foundAndRemoved = false;
        for (Member member : memberList.getMembers()) {
            if (member != null && member.getProfile().equals(cancelProfile)) {
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

    /**
     * Displays the class schedule with current attendees
     */
    private void displayClassSchedule() {
        System.out.println("-Fitness classes-");
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass != null) {
                System.out.println(fitnessClass);
                if (fitnessClass.getMembers().getSize() > 0) {
                    System.out.println("[Attendees]");
                    for (Member member : fitnessClass.getMembers().getMembers()) {
                        if (member != null) {
                            System.out.println("   " + member);
                        }
                    }
                }
                if (fitnessClass.getGuests().getSize() > 0) {
                    System.out.println("[Guests]");
                    for (Member guest : fitnessClass.getGuests().getMembers()) {
                        if (guest != null) {
                            System.out.println("   " + guest);
                        }
                    }
                }
            }
        }
        System.out.println("-end of class list.\n");
    }

    /**
     * Takes attendance of a member attending a class and adds the member to the class.
     * Ensures all necessary condition checks before adding a member to a class
     *
     * @param parts an array of strings, where each element represents a specific piece of information
     *              from the command line argument
     */
    private void memberClassAttendance(String[] parts) {
        String classString = parts[MEMBER_GUEST_CLASS_TYPE_INDEX];
        String instructorString = parts[MEMBER_GUEST_CLASS_INSTRUCTOR_INDEX];
        String studioString = parts[MEMBER_GUEST_CLASS_STUDIO_INDEX];
        String firstName = parts[MEMBER_GUEST_FIRST_NAME_INDEX];
        String lastName = parts[MEMBER_GUEST_LAST_NAME_INDEX];
        Date dob = new Date(parts[MEMBER_GUEST_DOB_INDEX]);
        if (!addToClassInputChecker(parts, classString, instructorString, studioString, firstName, lastName, dob)) {
            return;
        }
        Offer classType = Offer.valueOf(classString.toUpperCase());
        Instructor instructor = Instructor.valueOf(instructorString.toUpperCase());
        Location studio = Location.valueOf(studioString.toUpperCase());
        Profile profile = new Profile(firstName, lastName, dob);
        Member member = memberList.getMemberFromProfile(profile);
        if (member instanceof Basic && !member.getHomeStudio().equals(studio)) {
            System.out.println(firstName + " " + lastName + " is attending a class at " + studio.getName()
                    + " - [BASIC] home studio at " + member.getHomeStudio().getName());
            return;
        }
        FitnessClass targetClass = schedule.findClass(classType, instructor, studio);
        if (targetClass == null) {
            System.out.println(classString + " by " + instructorString + " does not exist at " + studioString);
            return;
        }
        if (targetClass.getMembers().contains(member)) {
            System.out.println(member.getProfile().getFname() + " "
                    + member.getProfile().getLname() + " is already in the class.");
            return;
        }
        if (timeConflictChecker(targetClass, profile, instructor, studio, member)) {
            return;
        }
        targetClass.addMember(member);
        if (member instanceof Basic) {
            ((Basic) member).addClass();
        }
        System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname()
                + " attendance recorded " + targetClass.getClassInfo() + " at " + targetClass.getStudio());
    }

    /**
     * Helper method to check for necessary condition checks for successful addition of a member to a class
     *
     * @param parts            an array of strings, where each element represents a specific piece of information
     *                         from the command line argument
     * @param classString      the class type string
     * @param instructorString the class instructor string
     * @param studioString     the class studio location string
     * @param firstName        first name of the member
     * @param lastName         last name of the member
     * @param dob              date of birth of the member
     * @return true if the input for adding a member to a class is valid; false otherwise
     */
    private boolean addToClassInputChecker(String[] parts, String classString,
                                           String instructorString, String studioString,
                                           String firstName, String lastName, Date dob) {
        if (parts.length != ADD_REMOVE_CLASS_INPUT_MAX) {
            System.out.println("Missing data tokens.");
            return false;
        }
        Offer classType;
        Instructor instructor;
        Location studio;
        try {
            classType = Offer.valueOf(classString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(classString + " - class name does not exist.");
            return false;
        }
        try {
            instructor = Instructor.valueOf(instructorString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(instructorString + " - instructor does not exist.");
            return false;
        }
        try {
            studio = Location.valueOf(studioString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioString + " - invalid studio location.");
            return false;
        }
        Profile profile = new Profile(firstName, lastName, dob);
        if (!memberList.containsProfile(profile)) {
            System.out.println(firstName + " " + lastName + " " + dob + " is not in the member database.");
            return false;
        }
        Member member = memberList.getMemberFromProfile(profile);
        if (member.isExpired()) {
            System.out.println(firstName + " " + lastName + " " + dob + " membership expired.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to check for time conflicts based on classes
     * a member may be in at the same time
     *
     * @param targetClass the target FitnessClass in which the member may be in
     * @param profile     the profile of the member
     * @param instructor  the instructor of the class
     * @param studio      the studio location of the class
     * @param member      the member whose time conflicts are checked
     * @return true if the member is currently in a class held at the same time;
     * false if no time conflicts
     */
    private boolean timeConflictChecker(FitnessClass targetClass, Profile profile,
                                        Instructor instructor, Location studio, Member member) {
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass != null && !fitnessClass.equals(targetClass)
                    && fitnessClass.getTime().equals(targetClass.getTime())
                    && fitnessClass.getMembers().contains(member)) {

                System.out.println("Time conflict - " + profile.getFname() + " " + profile.getLname() +
                        " is in another class held at " + fitnessClass.getTime() + " - " +
                        instructor + ", " + fitnessClass.getTime() + ", " + studio.getName());
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a member from a class.
     * Ensures all necessary condition checks before removing a member from a class
     *
     * @param parts an array of strings, where each element represents a specific piece of information
     *              from the command line argument
     */
    private void removeMemberFromClass(String[] parts) {
        if (parts.length != ADD_REMOVE_CLASS_INPUT_MAX) {
            System.out.println("Missing data tokens.");
            return;
        }
        String classString = parts[MEMBER_GUEST_CLASS_TYPE_INDEX];
        Instructor instructor = Instructor.valueOf(parts[MEMBER_GUEST_CLASS_INSTRUCTOR_INDEX].toUpperCase());
        Location studio = Location.valueOf(parts[MEMBER_GUEST_CLASS_STUDIO_INDEX].toUpperCase());
        String firstName = parts[MEMBER_GUEST_FIRST_NAME_INDEX];
        String lastName = parts[MEMBER_GUEST_LAST_NAME_INDEX];
        Date dob = new Date(parts[MEMBER_GUEST_DOB_INDEX]);
        Profile unregisterProfile = new Profile(firstName, lastName, dob);
        Member unregisterMember = memberList.getMemberFromProfile(unregisterProfile);
        Offer classType;
        try {
            classType = Offer.valueOf(classString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(classString + " - class name does not exist.");
            return;
        }
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass.equals(schedule.findClass(classType, instructor, studio))) {
                boolean removed = fitnessClass.removeMember(unregisterMember);
                if (removed) {
                    System.out.println(unregisterProfile.getFname() + " " +
                            unregisterProfile.getLname() + " is removed from " +
                            fitnessClass.getInstructor() + ", " + fitnessClass.getTime() + ", " +
                            fitnessClass.getStudio());
                    return;
                } else {
                    System.out.println(unregisterProfile.getFname() + " " +
                            unregisterProfile.getLname() + " is not in " +
                            fitnessClass.getInstructor() + ", " + fitnessClass.getTime() +
                            ", " + fitnessClass.getStudio());
                }
            }
        }
    }

    /**
     * Takes attendance of a guest attending a class and adds the guest to the class.
     * Ensures all necessary condition checks before adding a guest to a class
     *
     * @param parts an array of strings, where each element represents a specific piece of information
     *              from the command line argument
     */
    private void guestClassAttendance(String[] parts) {
        String classString = parts[MEMBER_GUEST_CLASS_TYPE_INDEX];
        String instructorString = parts[MEMBER_GUEST_CLASS_INSTRUCTOR_INDEX];
        String studioString = parts[MEMBER_GUEST_CLASS_STUDIO_INDEX];
        String firstName = parts[MEMBER_GUEST_FIRST_NAME_INDEX];
        String lastName = parts[MEMBER_GUEST_LAST_NAME_INDEX];
        Date dob = new Date(parts[MEMBER_GUEST_DOB_INDEX]);
        if (!addToClassInputChecker(parts, classString, instructorString,
                studioString, firstName, lastName, dob)) {
            return;
        }
        Offer classType = Offer.valueOf(classString.toUpperCase());
        Instructor instructor = Instructor.valueOf(instructorString.toUpperCase());
        Location studio = Location.valueOf(studioString.toUpperCase());
        Profile profile = new Profile(firstName, lastName, dob);
        Member member = memberList.getMemberFromProfile(profile);
        if (member instanceof Basic) {
            System.out.println(firstName + " " + lastName + " [BASIC] - no guest pass.");
            return;
        }
        if (!member.getHomeStudio().equals(studio)) {
            System.out.println(firstName + " " + lastName + " (guest) is attending a class at " +
                    studio.getName() + " - home studio at " + member.getHomeStudio().getName());
            return;
        }
        if (member instanceof Premium && ((Premium) member).getGuestPass() <= 0) {
            System.out.println(firstName + " " + lastName + " guest pass not available.");
            return;
        }
        if (member instanceof Family && !((Family) member).isGuest()) {
            System.out.println(firstName + " " + lastName + " guest pass not available.");
            return;
        }
        guestAddToClass(classType, instructor, studio, firstName, lastName, member);
    }

    /**
     * Helper method to add a guest to a fitness class
     *
     * @param classType  the type of the class
     * @param instructor the instructor of the class
     * @param studio     the studio location of the class
     * @param firstName  the first name of the guest
     * @param lastName   the last name of the guest
     * @param member     the guest who is to be added to the class
     */
    private void guestAddToClass(Offer classType, Instructor instructor, Location studio,
                                 String firstName, String lastName, Member member) {
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass.equals(schedule.findClass(classType, instructor, studio))) {

                fitnessClass.addGuest(member);
                if (member instanceof Premium) {
                    ((Premium) member).useGuestPass();
                }
                if (member instanceof Family) {
                    ((Family) member).setGuest(false);
                }
                System.out.println(firstName + " " + lastName + " (guest) attendance recorded " +
                        classType + " at " + fitnessClass.getStudio());
                return;
            }
        }
    }

    /**
     * Removes the guest from a class.
     * Ensures all necessary condition checks before removing a guest from a class
     *
     * @param parts an array of strings, where each element represents a specific piece of information
     *              from the command line argument
     */
    private void removeGuestFromClass(String[] parts) {
        if (parts.length != ADD_REMOVE_CLASS_INPUT_MAX) {
            System.out.println("Missing data tokens.");
            return;
        }
        String classString = parts[MEMBER_GUEST_CLASS_TYPE_INDEX];
        Instructor instructor = Instructor.valueOf(parts[MEMBER_GUEST_CLASS_INSTRUCTOR_INDEX].toUpperCase());
        Location studio = Location.valueOf(parts[MEMBER_GUEST_CLASS_STUDIO_INDEX].toUpperCase());
        String firstName = parts[MEMBER_GUEST_FIRST_NAME_INDEX];
        String lastName = parts[MEMBER_GUEST_LAST_NAME_INDEX];
        Date dob = new Date(parts[MEMBER_GUEST_DOB_INDEX]);
        Profile profile = new Profile(firstName, lastName, dob);
        Member unregisterGuest = memberList.getMemberFromProfile(profile);
        Offer classType;
        try {
            classType = Offer.valueOf(classString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(classString + " - class name does not exist.");
            return;
        }
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass.equals(schedule.findClass(classType, instructor, studio))) {
                fitnessClass.removeGuest(unregisterGuest);
                if (unregisterGuest instanceof Premium) {
                    ((Premium) unregisterGuest).addGuestPass();
                }
                if (unregisterGuest instanceof Family) {
                    ((Family) unregisterGuest).setGuest(true);
                }
                System.out.println(unregisterGuest.getProfile().getFname() + " " +
                        unregisterGuest.getProfile().getLname() + " (guest) is removed from " +
                        fitnessClass.getInstructor() + ", " + fitnessClass.getTime() + ", " +
                        fitnessClass.getStudio());
                return;
            }
        }
    }
}
