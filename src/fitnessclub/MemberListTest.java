package fitnessclub;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemberListTest {

    @Test
    public void addValidBasicMember() {
        MemberList memberList = new MemberList();
        Profile profileB = new Profile("Vivek", "Man", new Date("10/3/1994"));
        Date expireDateB = new Date().plusMonths(1);
        Member basicMember = new Basic(profileB, expireDateB, Location.FRANKLIN, 0);
        assertTrue(memberList.add(basicMember));
    }

    @Test
    public void addValidFamilyMember() {
        MemberList memberList = new MemberList();
        Profile profileF = new Profile("Ved", "Pat", new Date("10/15/1979"));
        Date expireDateF = new Date().plusMonths(3);
        Member familyMember = new Family(profileF, expireDateF, Location.EDISON, true);
        assertTrue(memberList.add(familyMember));
    }

    @Test
    public void addValidPremiumMember() {
        MemberList memberList = new MemberList();
        Profile profileP = new Profile("Bob", "Dylan", new Date("2/30/1999"));
        Date expireDateP = new Date().plusYears(1);
        Member premiumMember = new Premium(profileP, expireDateP, Location.PISCATAWAY, 3);
        assertTrue(memberList.add(premiumMember));
    }

    @Test
    public void addInvalidDuplicateBasicMember() {
        MemberList memberList = new MemberList();
        Profile profileB = new Profile("Vivek", "Man", new Date("10/3/1994"));
        Date expireDateB = new Date().plusMonths(1);
        Member basicMember = new Basic(profileB, expireDateB, Location.FRANKLIN, 0);
        memberList.add(basicMember);
        assertFalse(memberList.add(basicMember));
    }

    @Test
    public void addInvalidDuplicateFamilyMember() {
        MemberList memberList = new MemberList();
        Profile profileF = new Profile("Ved", "Pat", new Date("10/15/1979"));
        Date expireDateF = new Date().plusMonths(3);
        Member familyMember = new Family(profileF, expireDateF, Location.EDISON, true);
        memberList.add(familyMember);
        assertFalse(memberList.add(familyMember));
    }

    @Test
    public void addInvalidDuplicatePremiumMember() {
        MemberList memberList = new MemberList();
        Profile profileP = new Profile("Bob", "Dylan", new Date("2/30/1999"));
        Date expireDateP = new Date().plusYears(1);
        Member premiumMember = new Premium(profileP, expireDateP, Location.PISCATAWAY, 3);
        memberList.add(premiumMember);
        assertFalse(memberList.add(premiumMember));
    }

    @Test
    public void removeValidMember() {
        MemberList memberList = new MemberList();
        Profile profileP = new Profile("Shaun", "Yeet", new Date("1/13/2001"));
        Date expireDateP = new Date().plusYears(1);
        Member premiumMember = new Premium(profileP, expireDateP, Location.SOMERVILLE, 3);
        memberList.add(premiumMember);
        assertTrue(memberList.remove(premiumMember));
    }

    @Test
    public void removeInvalidNon_ExistingMember() {
        MemberList memberList = new MemberList();
        Profile profileP = new Profile("Shaun", "Yeet", new Date("1/13/2001"));
        Date expireDateP = new Date().plusYears(1);
        Member premiumMember = new Premium(profileP, expireDateP, Location.BRIDGEWATER, 3);
        assertFalse(memberList.remove(premiumMember));
    }
}