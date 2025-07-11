package models;
import java.util.*;
import utils.*;

public abstract class Person {
    protected String username;
    protected String password;
    protected String[] Name = new String[3];
    protected static final Scanner sc = new Scanner(System.in);

    public Person(String username, String password, String[] temporaryName) {
        this.username = username;
        this.password = password;
        this.Name[0] = General.capitalizeFirstLetter(temporaryName[0]);
        this.Name[1] = (temporaryName.length > 1 ? General.capitalizeFirstLetter(temporaryName[1]) : "");
        this.Name[2] = (temporaryName.length > 2 ? General.capitalizeFirstLetter(temporaryName[2]) : "");
    }

    public String getName() {
        return this.Name[0];
    }

    public Person(String username) {
        this.username = username;
    }

    public Person() {
    }

    public String getPassword() {
        return password;
    }

    public static String getNameFromUser(String input) {
        String namePattern = "^[A-Za-z ]+$";
        String name;

        do {
            System.out.print(input);
            name = sc.nextLine();

            if (!name.matches(namePattern)) {
                System.out.println(
                        General.RED + "Invalid name. " + General.RESET + "Please enter only alphabets and spaces.");
            }
        } while (!name.matches(namePattern));
        return name;
    }

}
