package me.etudes.hcf.api.faction.dtr;

public enum DtrState {
    FROZEN(0),
    REGEN(1),
    FULL(2);

    private int state;

    DtrState(int state) {
        this.state = state;
    }

    public static DtrState fromInt(int state) {
        DtrState output = null;
        switch (state) {
            case 0:
                output = DtrState.FROZEN;
                break;
            case 1:
                output = DtrState.REGEN;
                break;
            case 2:
                output = DtrState.FULL;
                break;
        }
        return output;
    }

    public int toInt() {
        return state;
    }
}
