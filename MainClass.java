import java.util.*;
import java.io.File;
import utils.*;
import models.*;

public class MainClass {

    private static final String USERNAME = "MgsKHiQZEQQNGw==";

    private static final String PASSWORD = "Nw0KABAWGwQKMwgWEgsK";

    private static final Scanner sc = new Scanner(System.in);

    private static final String KEY = "secret";

    public static boolean data;

    public static String encrypt(String input) {
        byte[] inputBytes = input.getBytes();
        byte[] keyBytes = KEY.getBytes();
        byte[] outputBytes = new byte[inputBytes.length];

        for (int i = 0; i < inputBytes.length; i++) {
            outputBytes[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
        }

        return Base64.getEncoder().encodeToString(outputBytes);
    }

    public static int[] displayMenu() {
        ArrayList<Integer> validChoices = new ArrayList<>();
        System.out.println(General.GREEN + "\n 1. Add Admin" + General.RESET);
        validChoices.add(1);

        if (!Admin.isEmpty()) {
            System.out.println(General.CYAN + " 2. Remove Admin" + General.RESET);
            System.out.println(General.GREEN + " 3. Enroll Student" + General.RESET);
            validChoices.add(2);
            validChoices.add(3);

            if (!Student.isEmpty()) {
                System.out.println(General.CYAN + " 4. View student info" + General.RESET);
                System.out.println(General.CYAN + " 5. Student Profile" + General.RESET);
                System.out.println(General.CYAN + " 6. Unenroll Student" + General.RESET);
                System.out.println(General.CYAN + " 7. SBG Settings" + General.RESET);

                validChoices.add(4);
                validChoices.add(5);
                validChoices.add(6);
                validChoices.add(7);
            }

            System.out.println(General.PURPLE + General.BOLD + " 8. Export Records" + General.RESET);
            System.out.println(General.PURPLE + General.BOLD + " 9. Save Progress" + General.RESET);
            validChoices.add(8);
            validChoices.add(9);
        }

        if (!data) {
            System.out.println(General.PURPLE + General.BOLD + "10. Fetch data" + General.RESET);
            validChoices.add(10);
        }

        System.out.println(General.RED + General.BOLD + "11. Exit Program" + General.RESET);
        validChoices.add(11);
        int[] choices = new int[validChoices.size()];

        for (int i = 0; i < choices.length; i++) {
            choices[i] = validChoices.get(i);
        }

        return choices;
    }

    private static void MenuForSBG() {

        int sbgChoice = 0;

        do {

            System.out.println(General.BOLD + "\nSBG Functionality Menu:" + General.RESET);
            System.out.println(General.CYAN + "1. Add a student to SBG" + General.RESET);
            System.out.println(General.CYAN + "2. Remove Existing Member" + General.RESET);
            System.out.println(General.CYAN + "3. Replace a student in SBG" + General.RESET);
            System.out.println(General.CYAN + "4. View SBG members" + General.RESET);
            System.out.println(General.PURPLE + "5. Return to Main Menu" + General.RESET);
            System.out.print("\nEnter your menu option: ");

            try {
                sbgChoice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(General.RED + "Please enter a valid integer." + General.RESET);
                continue;
            }

            switch (sbgChoice) {
                case 1:
                    SBG.addMember();
                    break;
                case 2:
                    SBG.removeMember();
                    break;
                case 3:
                    SBG.replaceMember();
                    break;
                case 4:
                    SBG.viewMembers();
                    break;
                case 5:
                    System.out.println(General.YELLOW + "Returning to the main menu." + General.RESET);
                    break;
                default:
                    System.out.println(General.RED + "Invalid SBG choice. Please try again." + General.RESET);
                    break;
            }

        } while (sbgChoice != 5);
    }

    final static void startingDisplay() {
        System.out.println(General.GREEN + General.BOLD +
                "\nWelcome to the DA-IICT Trustee Simulator!\nIn this program, you will experience what it is like to be a trustee of DA-IICT, a university in India.\nTo start, please follow these steps:\n- Enter your username.\n- Enter your password.\n- Sign in to the management portal of the university as a trustee.\n"
                +
                General.RESET);
    }

    final static void checktrustee() {
        boolean verified = false;
        boolean usernameCorrect = false;
        int loginAttempts = 0;

        do {
            if (!usernameCorrect) {
                System.out.print(General.PURPLE + "Enter the username: ");
                String inputUsername = sc.nextLine();

                if (USERNAME.equals(encrypt(inputUsername))) {
                    usernameCorrect = true;
                } else {
                    System.out.println(General.RED + "Invalid username!" + General.RESET);
                }
            }

            if (usernameCorrect) {
                System.out.println(General.RESET +
                        General.BOLD + "Hello, Anil sir. Welcome to our DA-IICT portal." + General.RESET);
                System.out.print(General.PURPLE + "Enter the password: ");
                String inputPassword = sc.nextLine();

                if (PASSWORD.equals(encrypt(inputPassword))) {
                    System.out.println(General.RESET + General.BOLD +
                            "You are successfully verified as the trustee of DA-IICT." + General.RESET);
                    verified = true;
                } else {
                    System.out.println(General.RED + "Invalid password!" + General.RESET);
                }
            }

            if (!verified) {
                loginAttempts++;

                if (loginAttempts < General.MAX_ATTEMPTS) {
                    System.out.println("You have now " + (General.MAX_ATTEMPTS - loginAttempts) + " chances.\n");
                } else {
                    System.out.println(General.RED + "Now you ran out of chances.\n" + General.RESET);
                    System.exit(0);
                }
            }
        } while (!verified && loginAttempts < General.MAX_ATTEMPTS);
    }

    public static void exitFunction(int old) {

        if (old != 9 && old != 4 && old != -1) {
            boolean validInput = false;
            System.out.println(General.CYAN + "\n1. Save & Close" + General.RESET);
            System.out.println(General.CYAN + "2. Exit Without Saving" + General.RESET);
            System.out.println(General.CYAN + "3. Cancel exit request" + General.RESET);

            while (!validInput) {
                System.out.print("\nPlease choose an option: ");
                try {
                    int temp = Integer.parseInt(sc.nextLine());

                    switch (temp) {
                        case 1:
                            DataSaver.rewriteData();
                            System.out.println(General.BOLD + General.YELLOW +
                                    "File saved successfully. Thank you!" + General.RESET);
                            System.exit(0);
                            break;
                        case 2:
                            System.out.println(
                                    General.BOLD + General.YELLOW + "Exiting without saving. Have a great day!" +
                                            General.RESET);
                            System.exit(0);
                            break;
                        case 3:
                            validInput = true;
                            break;
                        default:
                            System.out.println(
                                    General.RED + "Invalid option." + General.RESET + " Please enter 1, 2, or 3.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(General.RED + "Please enter a valid integer." + General.RESET);
                }
            }

        } else if (old == -1) {
            System.out.println(General.BOLD + General.YELLOW + "No changes made to save." + General.RESET);
            System.exit(0);
        } else {

            System.out
                    .println(General.BOLD + General.YELLOW + "File saved successfully. Thank you!" + General.RESET);
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        // startingDisplay();

        // checktrustee();

        File file = new File("Admin_database.csv");
        if (file.exists())
            data = DataSaver.fetchData("");
        else
            data = true;

        int choice = -1;
        int old;

        do {
            old = choice;
            int[] validChoices = displayMenu();
            System.out.print("\nEnter your choice :");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(General.RED + "Please enter a valid integer." + General.RESET);
                continue;
            }

            boolean isValid = false;

            for (int c : validChoices) {
                if (c == choice) {
                    isValid = true;
                    break;
                }
            }

            if (isValid) {

                switch (choice) {
                    case 1:
                        Admin.addAdmins();
                        break;
                    case 2:
                        Admin.removeAdmins();
                        break;
                    case 3:
                        Student.addStudents();
                        break;
                    case 4:
                        Student.showAllRegisteredStudents();
                        break;
                    case 5:
                        Student.editOrShowInformationOfRegisteredStudents();
                        break;
                    case 6:
                        Student.removeStudents();
                        break;
                    case 7:
                        MenuForSBG();
                        break;
                    case 8:
                        DataSaver.saveDataToTxtFile();
                        break;
                    case 9:
                        DataSaver.rewriteData();
                        System.out.println(General.BLUE + "Data saved successfully!");
                        break;
                    case 10:
                        data = DataSaver.fetchData("yes");
                        break;
                    case 11:
                        exitFunction(old);
                        break;

                }

            } else {
                System.out.println(General.RED + "Your input is invalid..." + General.RESET + "please try again...");
            }

        } while (true);

    }

}
