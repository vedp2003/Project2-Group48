package fitnessclub;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MemberListTest {

    private MemberList memberList;
    private Member basicMember, familyMember, premiumMember, nonExistingMember;

    @Before
    public void setUp() {
        memberList = new MemberList();
        Profile profile1 = new Profile("Vivek", "Man", new Date("10/3/1994"));
        Profile profile2 = new Profile("Ved", "Pat", new Date("10/15/1979"));
        Profile profile3 = new Profile("Bob", "Dylan", new Date("2/30/1999"));
        Profile nonExistingProfile = new Profile("Bad", "User", new Date("1/1/2010"));

        Date expireDateB = new Date().plusMonths(1);
        Date expireDateF = new Date().plusMonths(3);
        Date expireDateP = new Date().plusYears(1);

        basicMember = new Basic(profile1, expireDateB, Location.BRIDGEWATER, 0);
        familyMember = new Family(profile2, expireDateF, Location.EDISON, true);
        premiumMember = new Premium(profile3, expireDateP, Location.PISCATAWAY, 3);
        nonExistingMember = new Basic(nonExistingProfile, expireDateB, Location.SOMERVILLE, 0);
    }

    @Test
    public void addBasicMemberTrue() {
        assertTrue(memberList.add(basicMember));
    }

    @Test
    public void addFamilyMemberTrue() {
        assertTrue(memberList.add(familyMember));
    }

    @Test
    public void addPremiumMemberTrue() {
        assertTrue(memberList.add(premiumMember));
    }

    // False cases for adding duplicate members
    @Test
    public void addBasicMemberFalse() {
        memberList.add(basicMember);
        assertFalse(memberList.add(basicMember));
    }

    @Test
    public void addFamilyMemberFalse() {
        memberList.add(familyMember);
        assertFalse(memberList.add(familyMember));
    }

    @Test
    public void addPremiumMemberFalse() {
        memberList.add(premiumMember);
        assertFalse(memberList.add(premiumMember));
    }

    @Test
    public void removeMemberTrue() {
        memberList.add(basicMember);
        assertTrue(memberList.remove(basicMember));
    }

    // False case for removing a non-existent member
    @Test
    public void removeMemberFalse() {
        assertFalse(memberList.remove(nonExistingMember));
    }
}