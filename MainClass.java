import java.util.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class General {

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

abstract class Person {
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

    String getName() {
        return this.Name[0];
    }

    public Person(String username) {
        this.username = username;
    }

    public Person() {
    }

    String getPassword() {
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

class Admin extends Person {
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

    static boolean isEmpty() {
        return totalAdmins.isEmpty();
    }

    static int loginAdmin() {
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

    static void removeAdmins() {
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

class Student extends Person {

    public static ArrayList<Student> totalStudents = new ArrayList<>();
    long idOfStudent;
    int yearOfStudy;
    int isSBGmember = 0;

    void setIsSBGmember(int i) {
        this.isSBGmember = i;
    }

    Student() {
    }

    Student(long id) {
        this.idOfStudent = id;
        this.password = Long.toString(id);
    }

    Student(long id, String password, String[] temporaryName, int year) {
        this.idOfStudent = id;
        this.password = password;
        this.Name[0] = General.capitalizeFirstLetter(temporaryName[0]);
        this.Name[1] = (temporaryName.length > 1 ? General.capitalizeFirstLetter(temporaryName[1]) : "");
        this.Name[2] = (temporaryName.length > 2 ? General.capitalizeFirstLetter(temporaryName[2]) : "");
        this.yearOfStudy = year;
    }

    static boolean isEmpty() {
        return totalStudents.isEmpty();
    }

    @Override

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;
        Student student = (Student) obj;
        return idOfStudent == student.idOfStudent;
    }

    @Override

    public int hashCode() {
        return Objects.hash(idOfStudent);
    }

    static int loginStudent() {
        int attempts = 0;
        int studentIndex = -1;
        boolean idCorrect = false;

        do {
            if (!idCorrect) {
                System.out.print("Enter your student id: ");
                try {
                    long id = Long.parseLong(sc.nextLine());
                    Student student = new Student();
                    student.idOfStudent = id;
                    studentIndex = totalStudents.indexOf(student);

                    if (studentIndex == -1) {
                        System.out.print(General.RED + "Invalid id! " + General.RESET);
                        attempts++;
                    } else {
                        System.out.println("Hi, " + totalStudents.get(studentIndex).getName());
                        idCorrect = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.print(General.RED + "Invalid input! " + General.RESET);
                    attempts++;
                }
            }

            if (idCorrect) {
                System.out.print("Enter your password: ");
                String password = sc.nextLine();

                if (password.equals(totalStudents.get(studentIndex).password)) {
                    return studentIndex;
                } else {
                    System.out.println(General.RED + "Invalid password! " + General.RESET);
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

    static void removeStudents() {
        System.out.println("\nOnly administrators can remove students. Thus enter the ID and password.");
        int loginResult = Admin.loginAdmin();

        if (loginResult != -1) {
            System.out.println();
            int attempts = 0;
            boolean idCorrect = false;

            do {
                if (!idCorrect) {
                    System.out.print("Enter the ID of the student to be removed :");
                    try {
                        long id = Long.parseLong(sc.nextLine());
                        int index = totalStudents.indexOf(new Student(id));

                        if (index == -1) {
                            System.out.print(General.RED + "Invalid id! " + General.RESET);
                            attempts++;
                        } else {
                            if (totalStudents.get(index).isSBGmember == 1) {
                                SBG.sbgMembers.remove(new Student(id));
                            }

                            System.out.println(
                                    totalStudents.get(index).getName() + " is removed as a student successfully.");
                            totalStudents.remove(index);
                            idCorrect = true;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.print(General.RED + "Invalid input! " + General.RESET);
                        attempts++;
                    }
                }

                if (attempts < General.MAX_ATTEMPTS) {
                    System.out.println("You have now " + (General.MAX_ATTEMPTS - attempts) + " chances.\n");
                }
            } while (attempts < General.MAX_ATTEMPTS && !idCorrect);

            if (attempts == General.MAX_ATTEMPTS) {
                System.out.println("Now you ran out of chances.");
            }
        }
    }

    static void addStudents() {
        int temp = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("Enter the number of new students: ");
                temp = Integer.parseInt(sc.nextLine());

                if (temp > 0) {
                    valid = true;
                } else {
                    System.out.println("The number of students must be positive. Please try again.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println(General.RED + "Invalid input." + General.RESET);
            }
        }

        for (int i = 0; i < temp; i++) {
            String[] spitedString = getNameFromUser("\nEnter the full name of Student " + (i + 1) + ": ").split(" ", 3);
            long id = 0;
            valid = false;

            while (!valid) {
                try {
                    System.out.print("Enter the ID of " + spitedString[0] + ": ");
                    id = Long.parseLong(sc.nextLine());

                    if (!totalStudents.contains(new Student(id))) {
                        if (id > 0) {
                            valid = true;
                        } else {
                            System.out.println("The ID must be positive. Please try again.\n");
                        }
                    } else {
                        System.out.println("This ID is already in use. Please try a different one.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(General.RED + "Invalid input. Please enter a valid number." + General.RESET);
                }
            }

            String password;

            do {
                System.out.print("Enter the password of " + spitedString[0] + ": ");
                password = sc.nextLine();

                if (password.contains(" "))
                    System.out.println(General.RED + "Password does not contain space.\n" + General.RESET);
            } while (password.contains(" "));

            int year = 0;
            valid = false;

            while (!valid) {
                try {
                    System.out.print("Enter the current year of study: ");
                    year = Integer.parseInt(sc.nextLine());

                    if (year >= 1 && year <= 4) {
                        valid = true;
                    } else {
                        System.out.println(
                                General.RED + "Invalid year. Please enter a year between 1 and 4." + General.RESET);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(General.RED + "Invalid input.\n" + General.RESET);
                }
            }

            Student temporary = new Student(id, password, spitedString, year);
            totalStudents.add(temporary);
        }

        System.out
                .println(General.CYAN + "Now the total number of students is " + totalStudents.size() + General.RESET);
    }

    public static void showAllRegisteredStudents() {
        System.out.println(General.YELLOW + "\nAccess Restricted: Only administrators can view all student details.\n" +
                General.RESET);

        if (Admin.loginAdmin() != -1) {
            System.out.printf(General.BOLD + "\nTotal Students at DA-IICT: %d\n\n" + General.RESET,
                    totalStudents.size());
            String leftAlignFormat = General.RED + "|" + General.CYAN + " %-10s " + General.RED + "|" + General.CYAN +
                    " %-20s " +
                    General.RED + "|" + General.CYAN + " %-15s " +
                    General.RED + "|" + General.CYAN + " %-15s " + General.RED + "|" + General.CYAN + " %-10s " +
                    General.RED + "|\n" + General.RESET;

            System.out.format(General.BOLD + General.RED +
                    "+------------+----------------------+-----------------+-----------------+------------+\n" +
                    General.RESET);

            System.out.format(General.BOLD + General.RED +
                    "|" + General.PURPLE + " ID         " + General.RED + "|" + General.PURPLE +
                    " Name                 " +
                    General.RED + "|" + General.PURPLE + " Password        " + General.RED + "|" + General.PURPLE +
                    " Year of Study   " + General.RED + "|" + General.PURPLE + " SBG Member " + General.RED + "|\n" +
                    General.RESET);
            System.out.format(General.BOLD + General.RED +
                    "+============+======================+=================+=================+============+\n" +
                    General.RESET);

            for (Student student : totalStudents) {
                String sbgStatus = student.isSBGmember > 0 ? "Yes" : "No";
                System.out.format(leftAlignFormat, student.idOfStudent, student.getName(), student.getPassword(),
                        student.yearOfStudy, sbgStatus);
            }

            System.out.format(General.BOLD + General.RED +
                    "+------------+----------------------+-----------------+-----------------+------------+\n" +
                    General.RESET);
        }
    }

    static void editOrShowInformationOfRegisteredStudents() {
        System.out.println("\nOnly student himself can edit his information. Enter ID and password.");
        int studentIndex = Student.loginStudent();

        if (studentIndex != -1) {
            editStudentInformation(studentIndex);
            System.out.println("Information updated successfully.");
        }
    }

    private static void editStudentInformation(int index) {
        Student student = totalStudents.get(index);
        boolean done = false;

        do {
            System.out.println(General.PURPLE + General.BOLD + "\nCurrent information of the student:" + General.RESET);
            System.out.println(General.CYAN + "ID: " + General.RESET + student.idOfStudent);
            System.out.println(General.CYAN + "First Name: " + General.RESET + student.getName());
            System.out.println(
                    General.CYAN + "Middle Name: " + General.RESET + (student.Name.length > 1 ? student.Name[1] : ""));
            System.out.println(
                    General.CYAN + "Last Name: " + General.RESET + (student.Name.length > 2 ? student.Name[2] : ""));
            System.out.println(General.CYAN + "Password: " + General.RESET + student.password);
            System.out.println(General.CYAN + "Year of study: " + General.RESET + student.yearOfStudy);

            System.out.println(General.PURPLE + General.BOLD + "\nChoose the field to be edited:" + General.RESET);
            System.out.println("1. First Name");
            System.out.println("2. Middle Name");
            System.out.println("3. Last Name");
            System.out.println("4. Password");
            System.out.println("5. Year of study");
            System.out.println("6. Exit");

            System.out.print("Enter your choice (1-6): ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter the new first name: ");
                        String firstName = sc.nextLine();
                        student.Name[0] = General.capitalizeFirstLetter(firstName);
                        System.out.println("First Name updated successfully.");
                        break;
                    case 2:
                        System.out.print("Enter the new middle name: ");
                        String middleName = sc.nextLine();
                        student.Name[1] = General.capitalizeFirstLetter(middleName);
                        System.out.println("Middle Name updated successfully.");
                        break;
                    case 3:
                        System.out.print("Enter the new last name: ");
                        String lastName = sc.nextLine();
                        student.Name[2] = General.capitalizeFirstLetter(lastName);
                        System.out.println("Last Name updated successfully.");
                        break;
                    case 4:
                        System.out.print("Enter the new password: ");
                        String newPassword = sc.nextLine();
                        student.password = newPassword;
                        System.out.println("Password updated successfully.");
                        break;
                    case 5:
                        System.out.println("Only admin can edit the year of study.");
                        int loginadmin = Admin.loginAdmin();

                        if (loginadmin != -1) {
                            while (true) {
                                try {
                                    System.out.print("Enter the new year of study: ");
                                    int year = Integer.parseInt(sc.nextLine());

                                    if (year > 0 && year < 5) {
                                        student.yearOfStudy = year;
                                        System.out.println("Year of study updated successfully.");
                                        break;
                                    } else {
                                        System.out.println(General.RED + "Invalid year. " + General.RESET +
                                                "Please enter year between 1 and 4.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println(General.RED + "Invalid input." + General.RESET);
                                }
                            }
                        }

                        break;
                    case 6:
                        done = true;
                        System.out.println("Exiting edit mode. Changes saved.");
                        break;
                    default:
                        System.out.println(General.RED + "Invalid choice." + General.RESET +
                                " Please enter a valid option (1-6).");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println(
                        General.RED + "Invalid input." + General.RESET + " Please enter a valid choice (1-8).");
            }
        } while (!done);
    }

}

class SBG {

    private static final String INVALID_INPUT_MESSAGE = General.RED + "Invalid input." + General.RESET +
            " Please enter a valid ID.";

    private static final Scanner sc = new Scanner(System.in);
    static final ArrayList<Student> sbgMembers = new ArrayList<>();

    public static void addMember() {
        if (sbgMembers.size() >= General.SBG_MAX) {
            System.out.println("SBG is already full. Cannot add more members.");
            return;
        }

        long studentId;
        int attempts = 0;

        while (attempts < General.MAX_ATTEMPTS) {
            System.out.print("Enter the ID of the student to add to the SBG: ");
            try {
                studentId = Long.parseLong(sc.nextLine());
                Student student = findStudentById(studentId);

                if (student != null) {
                    if (student.isSBGmember == 1) {
                        System.out.println(student.getName() + " is already a member of the SBG.");
                    } else {
                        student.isSBGmember = 1;
                        sbgMembers.add(student);
                        System.out.println(student.getName() + " added to the SBG successfully.");
                        return;
                    }
                } else {
                    System.out.println("Student not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
            }

            attempts++;

            if (attempts < General.MAX_ATTEMPTS) {
                System.out.println("You have " + (General.MAX_ATTEMPTS - attempts) + " attempt(s) left.\n");
            }
        }

        if (attempts == General.MAX_ATTEMPTS) {
            System.out.println("You have exceeded the maximum number of attempts.");
        }
    }

    public static void replaceMember() {
        if (sbgMembers.isEmpty()) {
            System.out.println("SBG is empty. No member to replace.");
            return;
        }

        if (sbgMembers.size() == Student.totalStudents.size()) {
            System.out.println("All students are already members of the SBG. No member to replace.");
            return;
        }

        int attempts = 0;
        boolean replaced = false;
        Student oldStudent = null;

        while (!replaced && attempts < General.MAX_ATTEMPTS) {
            try {
                if (oldStudent == null) {
                    System.out.print("\nEnter the ID of the student to replace in the SBG: ");
                    long oldStudentId = Long.parseLong(sc.nextLine());
                    oldStudent = findStudentById(oldStudentId);

                    if (oldStudent == null || !sbgMembers.contains(oldStudent)) {
                        System.out.println("Student not found in the SBG.");
                        oldStudent = null;
                        attempts++;

                        if (attempts < 3)
                            System.out.println("You have " + General.RED + (General.MAX_ATTEMPTS - attempts) +
                                    General.RESET + " attempt(s) left.");
                        continue;
                    }
                }

                System.out.print("\nEnter the ID of the new student to add to the SBG: ");
                long newStudentId = Long.parseLong(sc.nextLine());
                Student newStudent = findStudentById(newStudentId);

                if (newStudent == null) {
                    System.out.println("New student not found.");
                } else if (sbgMembers.contains(newStudent)) {
                    System.out.println("New student is already a member of the SBG.");
                } else {
                    sbgMembers.remove(oldStudent);
                    oldStudent.isSBGmember = 0;
                    sbgMembers.add(newStudent);
                    newStudent.isSBGmember = 1;
                    System.out.println("Student replaced in SBG successfully.");
                    replaced = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
            }

            attempts++;

            if (!replaced && (General.MAX_ATTEMPTS - attempts) > 0) {
                System.out.println("You have " + General.RED + (General.MAX_ATTEMPTS - attempts) + General.RESET +
                        " attempt(s) left.");
            }
        }

        if (!replaced) {
            System.out.println(General.RED + "You have exceeded the maximum number of attempts." + General.RESET);
        }
    }

    public static void removeMember() {
        if (sbgMembers.isEmpty()) {
            System.out.println("SBG is empty. No member to remove.");
            return;
        }

        int attempts = 0;

        while (attempts < General.MAX_ATTEMPTS) {
            try {
                System.out.print("Enter the ID of the student to remove from the SBG: ");
                long studentIdToRemove = Long.parseLong(sc.nextLine());
                Student studentToRemove = findStudentById(studentIdToRemove);

                if (studentToRemove == null || !sbgMembers.contains(studentToRemove)) {
                    System.out.println("Student not found in the SBG.");
                } else {
                    sbgMembers.remove(studentToRemove);
                    studentToRemove.isSBGmember = 0;
                    System.out.println("Student removed from SBG successfully.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
            }

            attempts++;

            if (attempts < General.MAX_ATTEMPTS) {
                System.out.println("You have " + (General.MAX_ATTEMPTS - attempts) + " attempt(s) left.");
            }
        }

        if (attempts == General.MAX_ATTEMPTS) {
            System.out.println("You have exceeded the maximum number of attempts.");
        }
    }

    public static void viewMembers() {
        if (sbgMembers.isEmpty()) {
            System.out.println("SBG is empty. No member to view.");
            return;
        }

        System.out.println(General.GREEN + "+---------------------------+------------+" + General.RESET);
        System.out.printf(General.GREEN + "|" + General.RESET + " %-25s " + General.GREEN + "|" + General.RESET +
                " %-10s " + General.GREEN + "|" + General.RESET + "\n", "Name", "ID");
        System.out.println(General.GREEN + "+===========================+============+" + General.RESET);

        for (Student student : sbgMembers) {
            System.out.printf(
                    General.GREEN + "|" + General.RESET + " %-25s " + General.GREEN + "|" + General.RESET +
                            " %-10s " + General.GREEN + "|" + General.RESET + "\n",
                    student.getName(), student.idOfStudent);
        }

        System.out.println(General.GREEN + "+---------------------------+------------+" + General.RESET);

    }

    private static Student findStudentById(long id) {
        for (Student student : Student.totalStudents) {
            if (student.idOfStudent == id) {
                return student;
            }
        }

        return null;
    }
}

class DataSaver {

    private static final String ADMIN_DATABASE_FILE = "Admin_database.csv";

    private static final String STUDENT_DATABASE_FILE = "Student_database.csv";

    public static void saveDataToTxtFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Exported_file.txt"))) {
            writeAdminData(writer);
            writeStudentData(writer);
            System.out.println(General.BLUE + "Data successfully written to Exported_file");
        } catch (IOException e) {
            System.out.println(General.RED + "An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    private static void writeAdminData(BufferedWriter writer) throws IOException {
        writer.write(printline() + "-" + printspace() + " ADMIN DATA " + printspace() + "-\n" + printline());
        for (Admin admin : Admin.totalAdmins) {
            writer.write(formatAdminData(admin));
            writer.newLine();
        }

        writer.newLine();
        writer.newLine();
        writer.newLine();
    }

    private static void writeStudentData(BufferedWriter writer) throws IOException {
        if (!Student.isEmpty()) {
            writer.write(printline() + "-" + printspace() + "STUDENT DATA" + printspace() + "-\n" + printline());

            for (Student student : Student.totalStudents) {
                writer.write(formatStudentData(student));
                writer.newLine();
            }
        }
    }

    static String printline() {
        return "----------------------------------------------------------------------------------------------------------------------------\n";
    }

    static String printspace() {
        return "                                                       ";
    }

    private static String formatAdminData(Admin admin) {
        return String.format("Admin Username: %-11s | Password: %-15s | Name: %-30s",
                admin.getUsername(), admin.getPassword(), String.join(" ", admin.Name));
    }

    private static String formatStudentData(Student student) {
        return String.format("Student ID: %-15d | Password: %-15s | Name: %-25s | Year: %-4d  | %-2s",
                student.idOfStudent, student.getPassword(), String.join(" ", student.Name), student.yearOfStudy,
                student.isSBGmember == 1 ? "SBG member" : "");
    }

    public static void rewriteData() {
        rewriteAdminDatabase();
        rewriteStudentDatabase();
    }

    private static void rewriteAdminDatabase() {
        try (BufferedWriter pw = new BufferedWriter(new FileWriter(ADMIN_DATABASE_FILE))) {
            for (Admin admin : Admin.totalAdmins) {
                StringJoiner joiner = new StringJoiner(",");
                joiner.add(admin.getUsername())
                        .add(admin.getPassword())
                        .add(String.join(" ", admin.Name));
                pw.write(joiner.toString());
                pw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing admin database: " + e.getMessage());
        }
    }

    private static void rewriteStudentDatabase() {
        try (BufferedWriter pw = new BufferedWriter(new FileWriter(STUDENT_DATABASE_FILE))) {
            for (Student student : Student.totalStudents) {
                StringJoiner joiner = new StringJoiner(",");
                joiner.add(Long.toString(student.idOfStudent))
                        .add(student.getPassword()).add(Integer.toString(student.yearOfStudy))
                        .add(Integer.toString(student.isSBGmember)).add(String.join(" ",
                                student.Name));
                pw.write(joiner.toString());
                pw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing student database: " + e.getMessage());
        }
    }

    public static boolean fetchData(String str) {
        final Scanner sc = new Scanner(System.in);
        String userInput = str;

        if ("yes".equalsIgnoreCase(userInput)) {
            return processYes();
        } else if ("no".equalsIgnoreCase(userInput)) {
            System.out.println(General.GREEN + "Data fetch operation was cancelled by the user." + General.RESET);
            return false;
        }

        while (true) {
            System.out.print(General.BOLD + "\nDo you want to fetch data? (yes/no) : " + General.RESET);
            userInput = sc.nextLine();

            if ("yes".equalsIgnoreCase(userInput)) {
                return processYes();
            } else if ("no".equalsIgnoreCase(userInput)) {
                System.out.println(General.GREEN + "Data fetch operation was cancelled by the user." + General.RESET);
                return false;
            } else {
                System.out.println(General.RED + "Invalid input. Please type 'yes' or 'no'." + General.RESET);
            }
        }
    }

    private static boolean processYes() {
        try {
            if (!fetchAdminData()) {
                System.out.println(General.RED + "Admin data is not available." + General.RESET);
            }

            if (!fetchStudentData()) {
                System.out.println(General.RED + "Student data is not available." + General.RESET);
            }

            return true;
        } catch (Exception e) {
            System.out.println(
                    General.RED + "An error occurred while fetching data: " + e.getMessage() + General.RESET);
            return false;
        }
    }

    private static boolean fetchAdminData() {
        try {
            List<String[]> adminData = readCSV(ADMIN_DATABASE_FILE);

            for (String[] data : adminData) {
                Admin admin = new Admin(data[0], data[1], data[2].split(" "));
                Admin.totalAdmins.add(admin);
            }

            return true;
        } catch (IOException e) {
            return false;
        }

    }

    private static boolean fetchStudentData() {
        try {
            List<String[]> studentData = readCSV(STUDENT_DATABASE_FILE);

            for (String[] data : studentData) {
                long id = Long.parseLong(data[0]);
                String password = data[1];
                int yearOfStudy = Integer.parseInt(data[2]);
                int sbgMember = Integer.parseInt(data[3]);
                Student student = new Student(id, password, data[4].split(" "), yearOfStudy);
                student.isSBGmember = sbgMember;

                if (sbgMember == 1) {
                    SBG.sbgMembers.add(student);
                }

                Student.totalStudents.add(student);
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static List<String[]> readCSV(String fileName) throws IOException {
        List<String[]> data = new ArrayList<>();
        File file = new File(fileName);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    data.add(line.split(","));
                }
            }
        } else {
            System.out.println("File not found: " + fileName);
        }

        return data;
    }

}

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
