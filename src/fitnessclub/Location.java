package fitnessclub;

public enum Location {
    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08837"),
    FRANKLIN("Somerset", "08873"),
    PISCATAWAY("Middlesex", "08854"),
    SOMERVILLE("Somerset", "08876");

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
}
