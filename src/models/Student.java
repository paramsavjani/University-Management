package models;
import java.util.*;
import utils.*;

public class Student extends Person {

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

    public static boolean isEmpty() {
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

    public static int loginStudent() {
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

    public static void removeStudents() {
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

    public static void addStudents() {
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

    public static void editOrShowInformationOfRegisteredStudents() {
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