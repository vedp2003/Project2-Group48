package fitnessclub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 *
 * @author Ved Patel, Vivek Manthri
 */
public class MemberList {
    private static final int INITIAL_CAPACITY = 4;
    private static final int GROW_CAPACITY = 4;
    private static final int NOT_FOUND = -1;

    private Member[] members; //holds Basic, Family, or Premium objects
    private int size; //number of objects in the array

    public MemberList() {
        members = new Member[INITIAL_CAPACITY];
        size = 0;
    }

    public Member[] getMembers() {
        return members;
    }

    public int getSize() {
        return size;
    }

    private int find(Member member) {
        for (int index = 0; index < size; index++) {
            if (members[index].compareTo(member) == 0) {
                return index;
            }
        }
        return NOT_FOUND;
    }

    private void grow() {
        Member[] growMember = new Member[members.length + GROW_CAPACITY];
        for (int i = 0; i < members.length; i++) {
            growMember[i] = members[i];
        }
        members = growMember;
    }

    public boolean contains(Member member) {
        //return find(member) != NOT_FOUND;

        for (int i = 0; i < size; i++) {
            if (members[i] != null && members[i].getProfile().equals(member.getProfile())) {
                return true;
            }
        }
        return false;

    }

    public boolean containsProfile(Profile profile) {
        for (Member member : this.members) {
            if (member != null && member.getProfile().equals(profile)) {
                return true;
            }
        }
        return false;
    }

    public Member getMemberFromProfile(Profile profile) {
        int memberIndex = NOT_FOUND;
        for (int i = 0; i < members.length; i++) {
            if (members[i] != null && members[i].getProfile().equals(profile)) {
                memberIndex = i;
                break;
            }
        }
        return members[memberIndex];
    }

    public boolean add(Member member) {
        if (contains(member)) {
            return false;
        }
        if (size == members.length) {
            grow();
        }
        members[size] = member;
        size++;
        return true;
    }

    public boolean remove(Member member) {
        int memberIndex = find(member);
        if (memberIndex == NOT_FOUND) {
            return false;
        }
        for (int i = memberIndex; i < size - 1; i++) {
            members[i] = members[i + 1];
        }
        members[size - 1] = null;
        size--;
        return true;

    }

    public void load(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        String inputStr;
        while (scanner.hasNextLine()) {
            inputStr = scanner.nextLine();
            if (inputStr.isEmpty()) {
                inputStr = scanner.nextLine();
            }
            String[] strSplit = inputStr.split("\\s");

            String memberType = strSplit[0];
            String firstName = strSplit[1];
            String lastName = strSplit[2];
            Date dob = new Date(strSplit[3]);
            Date expire = new Date(strSplit[4]);
            Location homeStudio = Location.valueOf(strSplit[5].toUpperCase());

            Member member;
            Profile memberProfile = new Profile(firstName, lastName, dob);
            if (memberType.equals("B")) {
                member = new Basic(memberProfile, expire, homeStudio, 0);
                add(member);
            }
            if (memberType.equals("F")) {
                member = new Family(memberProfile, expire, homeStudio, true);
                add(member);
            }
            if (memberType.equals("P")) {
                member = new Premium(memberProfile, expire, homeStudio, 3);
                add(member);
            }
        }
        scanner.close();

    }

    public void printByCounty() {
        for (int i = 1; i < size; i++) {
            Member key = members[i];
            int j = i - 1;

            while (j >= 0 && ((members[j].getHomeStudio().getCounty().compareTo(key.getHomeStudio().getCounty()) > 0)
                        || (members[j].getHomeStudio().getCounty().compareTo(key.getHomeStudio().getCounty()) == 0
                        && members[j].getHomeStudio().getZipCode().compareTo(key.getHomeStudio().getZipCode()) > 0))) {

                members[j + 1] = members[j];
                j -= 1;
            }
            members[j + 1] = key;
        }

        System.out.println("-list of members sorted by county then zipcode-");
        for (Member member : members) {
            if (member != null) {
                System.out.println(member);
            }
        }
        System.out.println("-end of list-\n");

    }//sort by county then zip code

    public void printByMember() {
        for (int i = 1; i < size; i++) {
            Member key = members[i];
            int j = i - 1;

            while (j >= 0 && members[j].compareTo(key) == 1) {
                members[j + 1] = members[j];
                j -= 1;
            }
            members[j + 1] = key;
        }

        System.out.println("\n-list of members sorted by member profiles-");

        for (Member member : members) {
            if (member != null) {
                System.out.println(member);
            }
        }
        System.out.println("-end of list-\n");

    } //sort by member profile

    public void printFees() {

        System.out.println("\n-list of members with next dues-");
        for (Member member : members) {
            if (member != null) {
                System.out.println(member + " [next due: $" + String.format("%.2f", member.bill()) + "]");
            }
        }
        System.out.println("-end of list-\n\n");
    } //print the array as is with the next due amounts
}
