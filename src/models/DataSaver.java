package models;
import java.io.BufferedWriter;
import java.util.*;
import utils.*;
import java.io.*;

public class DataSaver {

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

    public static String printline() {
        return "----------------------------------------------------------------------------------------------------------------------------\n";
    }

    public static String printspace() {
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
