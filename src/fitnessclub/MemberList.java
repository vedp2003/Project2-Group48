package fitnessclub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MemberList {
    private static final int INITIAL_CAPACITY = 4;
    private static final int GROW_CAPACITY = 4;
    private static final int NOT_FOUND = -1;

    private Member [] members; //holds Basic, Family, or Premium objects
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
        if (members[members.length - 1] != null) {
            Member[] growMember = new Member[members.length + GROW_CAPACITY];
            for (int i = 0; i < members.length; i++) {
                growMember[i] = members[i];
            }
            members = growMember;
        }

    }

    public boolean contains(Member member) {
        return find(member) != NOT_FOUND;
    }

    public boolean containsProf(Profile profile) {
        for (Member member : this.members) {
            if (member != null && member.getProfile().equals(profile)) {
                return true;
            }
        }
        return false;
    }

    public boolean add(Member member) {
        if (contains(member)) {
            return false;
        }
        if (size >= members.length) { //intially had this as ==
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
        members[memberIndex] = null;
        for (int i = memberIndex; i < size - 1; i++) {
            members[i] = members[i + 1];
        }
        size--;
        return true;
    }

    public void load(File file) throws IOException {

        Scanner scanner = new Scanner(file);
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()){
                line = scanner.nextLine();
            }
            String[] parts = line.split("\\s");
            String type = parts[0];
            String firstName = parts[1];
            String lastName = parts[2];
            Date dob = new Date(parts[3]);
            Date expire = new Date(parts[4]);
            Location homeStudio = Location.valueOf(parts[5].toUpperCase());

            Member member;
            switch (type) {
                case "B":
                    member = new Basic(new Profile(firstName, lastName, dob), expire, homeStudio, 0);
                    this.add(member);
                    break;
                case "F":
                    member = new Family(new Profile(firstName, lastName, dob), expire, homeStudio, false);
                    this.add(member);
                    break;
                case "P":
                    member = new Premium(new Profile(firstName, lastName, dob), expire, homeStudio, 3);
                    this.add(member);
                    break;
                default:
                    break;
            }
        }
        scanner.close();
        System.out.println("-list of members loaded-");
        printMembers();
        System.out.println("-end of list-");

    }//from the text file

    public void printMembers() {
        for (int i = 0; i < size; i++) {
            System.out.println(members[i]);
        }
    }
    public boolean sameProfile(Profile profile) {
        for (int i = 0; i < size; i++) {
            if(members[i].getProfile().equals(profile)) {
                return true;
            }
        }
        return false;
    }

    public void printByCounty() {
        for (int i = 1; i < size; i++) {
            Member key = members[i];
            int j = i - 1;

            while (j >= 0 && compareByCounty(members[j], key) > 0) {
                members[j + 1] = members[j];
                j--;
            }
            members[j + 1] = key;
        }

        //MAYBE NO SYSTEM.OUT ALLOWED - check
        for (Member member : members) {
            if (member != null) {
                System.out.println(member);
            }
        }
    }//sort by county then zip code

    private int compareByCounty(Member m1, Member m2) {
        int countyComparison = m1.getHomeStudio().getCounty().compareTo(m2.getHomeStudio().getCounty());
        if (countyComparison != 0) {
            return countyComparison;
        }
        return m1.getHomeStudio().getZipCode().compareTo(m2.getHomeStudio().getZipCode());
    }


    public void printByMember() {
        for (int i = 1; i < size; i++) {
            Member key = members[i];
            int j = i - 1;

            while (j >= 0 && members[j].compareTo(key) > 0) {
                members[j + 1] = members[j];
                j--;
            }
            members[j + 1] = key;
        }

        //MAYBE NO SYSTEM.OUT ALLOWED - check
        for (Member member : members) {
            if (member != null) {
                System.out.println(member);
            }
        }
    } //sort by member profile

    public void printFees() {

    } //print the array as is with the next due amounts
}
