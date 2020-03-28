package supsi.mobile_systems.models;

public enum Qualification {
    PROFESSOR(0),
    RESEARCHER(1),
    ASSISTANT(2);

    private int code;

    Qualification(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
