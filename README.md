# Integrated College Management System

## Overview

This project is a Java-based Integrated College Management System that utilizes core Object-Oriented Programming (OOP) concepts, including classes, inheritance, encapsulation, abstraction, and polymorphism. It allows for management of administrators and students in a college, including enrollment, credential management, and operations related to the Student Body Government (SBG).

## Features

* **Secure Trustee Login:** Trustee credentials (username/password) are encrypted and verified at startup, with a maximum of three attempts.
* **Admin Management:**

  * Add or remove admins.
  * Admin credentials and details are stored in CSV files.
  * Login system with username/password validation.
* **Student Management:**

  * Enroll new students with attributes like ID, year of study, and SBG membership.
  * Remove students from the system.
  * Edit student details (except year of study for students themselves).
  * Students have a login system similar to admins.
* **SBG Management:**

  * Add, remove, or replace SBG members from the pool of enrolled students.
  * View list of current SBG members.
* **Data Persistence:**

  * Uses CSV files to persist data across sessions.
  * Data is saved by creating new files on updates, leveraging Java's `ArrayList` for in-memory storage.
  * Data export to TXT files for record-keeping.
* **UML Diagrams:**

  * Project includes class and use-case diagrams describing the flow and interactions of components.

## Technologies Used

* Java (OOP, Collections, File I/O)
* CSV and TXT file handling

## How to Run

1. Compile the Java files using a Java compiler (e.g., `javac`).
2. Run the `MainClass`.
3. Use the following trustee credentials when prompted:

   * **Username:** AnilAmbani
   * **Password:** DhirubhaiAmbani
4. Follow on-screen menus to perform administrative tasks, student management, and SBG operations.

## Important Notes

* The system creates `Admin_database.csv` and `Student_database.csv` at runtime if they do not exist.
* Password encryption uses a simple XOR with a secret key ("secret") for basic obfuscation.
* Files are updated by overwriting with new data when changes are made.
* Trustee credentials are encrypted in the code and cannot be decrypted once compiled.

## Future Improvements

* Switch to using a relational database instead of CSV files.
* Enhance encryption with stronger algorithms.
* Implement a GUI using JavaFX or Swing.
* Add role-based access control for finer-grained permissions.

## Authors

* Nidhi Dodiya (202303009)
* Param Savjani (202303046)

## License

This project is for educational purposes.
