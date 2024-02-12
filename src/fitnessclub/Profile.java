package fitnessclub;

public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public Date getDob() {
        return dob;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile profile = (Profile) obj;
            return this.fname.equals(profile.fname) && this.lname.equals(profile.lname)
                    && this.dob.equals(profile.dob);
        }
        return false;
    }

    @Override
    public String toString() {
        return fname + ":" + lname + ":" + dob.toString();
    }


    @Override
    public int compareTo(Profile o) {
        int lastNameCompare = lname.compareTo(o.lname);
        int firstNameCompare = fname.compareTo(o.fname);
        if(lastNameCompare != 0) {
            return lastNameCompare;
        }
        if(firstNameCompare != 0) {
            return firstNameCompare;
        }
        return dob.compareTo(o.dob);
    }
}
