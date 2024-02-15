package fitnessclub;

public enum Location {
    BRIDGEWATER("SOMERSET", "08807"),
    EDISON("MIDDLESEX", "08837"),
    FRANKLIN("SOMERSET", "08873"),
    PISCATAWAY("MIDDLESEX", "08854"),
    SOMERVILLE("SOMERSET", "08876");

    private final String county;
    private final String zipCode;

    Location(String county, String zipCode) {
        this.county = county;
        this.zipCode = zipCode;
    }

    public String getCounty() {
        return county;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getName() {
        return this.name();
    }

    //Or u can do Location.EDISON.name(); i think --> without getName() method


    @Override
    public String toString() {
        return this.name() + ", " + this.zipCode + ", " + this.county;
    }
}
