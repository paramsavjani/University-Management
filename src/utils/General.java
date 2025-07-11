package utils;

public class General {

    public static final int MAX_ATTEMPTS = 3;

    public static final int SBG_MAX = 5;

    public final static String RESET = "\u001B[0m";

    public final static String RED = "\u001B[31m";

    public final static String GREEN = "\u001B[32m";

    public final static String YELLOW = "\u001B[33m";

    public final static String BLUE = "\u001B[34m";

    public final static String PURPLE = "\u001B[35m";

    public final static String CYAN = "\u001B[36m";

    public final static String BOLD = "\u001B[1m";

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return (input.substring(0, 1).toUpperCase() + input.substring(1)).split(" ")[0];
    }
}

