package models;
import java.util.*;
import utils.*;
public class SBG {

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

