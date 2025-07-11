package models;
import java.util.*;
import utils.*;


public class Admin extends Person {
    static ArrayList<Admin> totalAdmins = new ArrayList<>();

    Admin(String username, String password, String[] temporaryName) {
        super(username, password, temporaryName);
    }

    Admin(String username) {
        super(username);
    }

    Admin() {
    }

    String getUsername() {
        return super.username;
    }

    void setUsername(String username) {
        super.username = username;
    }

    @Override

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;
        Admin admin = (Admin) obj;
        return super.username.equals(admin.getUsername());
    }

    @Override

    public int hashCode() {
        return Objects.hash(getUsername());
    }

    public static boolean isEmpty() {
        return totalAdmins.isEmpty();
    }

    public static int loginAdmin() {
        int attempts = 0;
        int adminIndex = -1;
        boolean usernameCorrect = false;

        do {
            if (!usernameCorrect) {
                System.out.print("Enter your username: ");
                String username = sc.nextLine();
                Admin admin = new Admin();
                admin.setUsername(username);
                adminIndex = totalAdmins.indexOf(admin);

                if (adminIndex == -1) {
                    System.out.print(General.RED + "Invalid username! " + General.RESET);
                    attempts++;
                } else {
                    System.out.println("Hi, " + totalAdmins.get(adminIndex).getName());
                    usernameCorrect = true;
                }
            }

            if (usernameCorrect) {
                System.out.print("Enter your password: ");
                String password = sc.nextLine();

                if (password.equals(totalAdmins.get(adminIndex).password)) {
                    return adminIndex;
                } else {
                    System.out.println(General.RED + "Invalid password!" + General.RESET);
                    attempts++;
                }
            }

            if (attempts < General.MAX_ATTEMPTS) {
                System.out.println("You have now " + (General.MAX_ATTEMPTS - attempts) + " chances.\n");
            }
        } while (attempts < General.MAX_ATTEMPTS);
        System.out.println("Now you ran out of chances.\n");
        return -1;
    }

    public static void addAdmins() {
        int n = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("Enter the number of members as admins: ");
                n = Integer.parseInt(sc.nextLine());

                if (n > 0) {
                    valid = true;
                } else {
                    System.out.println(General.RED + "The number of admins must be positive." + General.RESET +
                            " Please try again.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println(General.RED + "Invalid input." + General.RESET + " Please enter a number.\n");
            }
        }

        for (int i = 0; i < n; i++) {
            String[] temporaryName = getNameFromUser("\nEnter the full name of Admin" + (i + 1) + ": ").split(" ", 3);
            String username;
            String password;

            do {
                System.out.print("Enter the username of " + temporaryName[0] + ": ");
                username = sc.nextLine();

                if (username.contains(" ")) {
                    System.out.println(General.RED + "Spaces are not allowed in the username." + General.RESET + "\n");
                } else if (totalAdmins.contains(new Admin(username))) {
                    System.out.println(General.RED + "This username is already in use. Please try a different one." +
                            General.RESET + "\n");
                }
            } while (username.contains(" ") || totalAdmins.contains(new Admin(username)));

            do {
                System.out.print("Enter the password of " + temporaryName[0] + ": ");
                password = sc.nextLine();

                if (password.contains(" ")) {
                    System.out
                            .println(General.RED + "Password cannot contain spaces. Please try again." + General.RESET +
                                    "\n");
                }
            } while (password.contains(" "));

            Admin temporary = new Admin(username, password, temporaryName);
            totalAdmins.add(temporary);
        }

        System.out.println(General.CYAN + "Now the total number of administrators is " + totalAdmins.size());
    }

    public static void removeAdmins() {
        for (int i = 0; i < General.MAX_ATTEMPTS; i++) {
            System.out.print("Enter username of that administrators :");
            String username = sc.nextLine();
            Admin s = new Admin(username);
            int index = totalAdmins.indexOf(s);

            if (index == -1) {
                if (i == 2) {
                    System.out.println(
                            General.RED + "Invalid username!" + General.RESET + "Now you ran out of chances.");
                    break;
                } else {
                    System.out.println(
                            General.RED + "Invalid username!" + General.RESET + "You have now " +
                                    (General.MAX_ATTEMPTS - 1 - i) + " chances.\n");
                }
            } else {
                System.out.println(totalAdmins.get(index).Name[0] + " is removed as a administrators successfully.");
                totalAdmins.remove(index);
                break;
            }
        }
    }

}