package me.etudes.hcf.api.deathban;

public class DeathbanUtils {

    public static final long DEATHBAN_MS = 3600000;
    public static final long REGEN_MS = 3600000;
    public static final long REGEN_TICKS = REGEN_MS / 50;

    private DeathbanUtils() {}

    public static String formatDeathbanTime(long time) {
        long seconds = time / 1000;
        int minutes = 0;
        while(seconds >= 60) {
            seconds -= 60;
            minutes++;
        }

        return minutes + " minutes, and " + seconds + " seconds";
    }

}
