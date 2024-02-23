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
            return this.fname.equalsIgnoreCase(profile.fname) && this.lname.equalsIgnoreCase(profile.lname)
                    && this.dob.equals(profile.dob);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.fname + ":" + this.lname + ":" + this.dob;
    }

    @Override
    public int compareTo(Profile o) {
        if (this.lname.compareToIgnoreCase(o.lname) > 0) {
            return 1;
        } else if (this.lname.compareToIgnoreCase(o.lname) < 0) {
            return -1;
        }

        if (this.fname.compareTo(o.fname) > 1) {
            return 1;
        } else if (this.fname.compareTo(o.fname) < 0) {
            return -1;
        }

        if (this.dob.compareTo(o.dob) == 1) {
            return 1;
        } else if (this.dob.compareTo(o.dob) == -1) {
            return -1;
        }
        return 0;
    }
}
