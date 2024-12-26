package hr.mlinx.data;

public enum CourtType {
    BACKCOURT("backcourt"), FRONTCOURT("frontcourt");

    private final String courtName;

    CourtType(String value) {
        courtName = value;
    }

    public String getCourtName() {
        return courtName;
    }
}
