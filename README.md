# Digital Forensics Evidence Manager

A Java-based educational application for managing digital investigation cases, evidence records, chain of custody, and evidence integrity verification using SHA-256 hashing.

The project demonstrates object-oriented programming, modular design, file handling, input validation, exception handling, and basic digital evidence management concepts.

---

## 1. Project Overview

The **Digital Forensics Evidence Manager** is a console-based Java application designed to organize information related to fictional digital investigations.

It allows an investigator to:

* Create and manage investigation cases
* Register and search digital evidence
* Maintain evidence chain-of-custody records
* Generate SHA-256 hashes for evidence files
* Verify whether an evidence file has been modified
* View basic investigation summaries
* Store project data using local files

The project is designed for educational purposes and focuses on demonstrating Java programming and software engineering concepts.

---

## 2. Main Modules

### Case Management

* Create investigation cases
* List existing cases
* Search cases
* Update case status

### Evidence Management

* Register evidence against a case
* Store evidence metadata
* Search and list evidence
* Associate evidence with investigators

### Chain of Custody

* Record evidence transfers
* Track the person releasing and receiving evidence
* View the custody history of an evidence item

### Evidence Integrity Verification

* Generate SHA-256 hash values
* Store the original hash of evidence
* Recalculate the hash during verification
* Detect modifications to evidence files

### Investigation Summary

* Display total cases
* Display total evidence records
* View investigation-related information

---

## 3. Technologies Used

* **Java 17+**
* Object-Oriented Programming
* Java Collections Framework
* Java File I/O / NIO
* Java Time API
* Java Security API
* SHA-256 Cryptographic Hashing
* Git and GitHub

No external database or framework is required.

---

## 4. Project Structure

```text
DigitalForensicsEvidenceManager/
│
├── src/
│   ├── model/
│   │   ├── User.java
│   │   ├── Investigator.java
│   │   ├── CaseRecord.java
│   │   ├── Evidence.java
│   │   └── CustodyRecord.java
│   │
│   ├── service/
│   │   ├── CaseService.java
│   │   ├── EvidenceService.java
│   │   └── CustodyService.java
│   │
│   ├── util/
│   │   ├── FileManager.java
│   │   ├── HashUtil.java
│   │   └── InputValidator.java
│   │
│   └── Main.java
│
├── data/
│   ├── sample_evidence.txt
│   └── README.txt
│
├── tests/
│   └── TestPlan.txt
│
├── screenshots/
│
├── README.md
├── statement.md
├── .gitignore
├── run.bat
└── run.sh
```

---

## 5. Requirements

Before running the project, install:

* Java Development Kit (**JDK 17 or newer**)
* Command Prompt / Terminal

Verify the installation using:

```bash
java -version
javac -version
```

Both commands should display Java version information.

---

## 6. How to Run

### Windows — Manual Method

Open Command Prompt and navigate to the project directory:

```bat
cd /d "C:\Users\Nishika\OneDrive\Documents\vityarthi java\DigitalForensicsEvidenceManager"
```

Compile the source files:

```bat
javac -d out src\model\*.java src\service\*.java src\util\*.java src\Main.java
```

Run the application:

```bat
java -cp out Main
```

### Using `run.bat`

The project also contains a `run.bat` file that can automatically compile and run the application.

If Windows blocks the batch file, the manual commands above can be used instead.

---

## 7. Basic Usage

After starting the application, use the displayed menu to access the different modules.

A typical demonstration can follow this sequence:

1. Create a new investigation case.
2. Add an evidence record to the case.
3. Register `data/sample_evidence.txt` as the evidence file.
4. Allow the application to calculate its SHA-256 hash.
5. Verify the evidence integrity.
6. Add chain-of-custody records.
7. View the custody history.
8. Generate the investigation summary.

---

## 8. SHA-256 Integrity Verification

The project uses SHA-256 hashing to identify changes to an evidence file.

### Demonstration

1. Add `data/sample_evidence.txt` as evidence.
2. The application calculates and stores its SHA-256 hash.
3. Run **Verify Evidence Integrity**.
4. If the file has not changed, the result is:

```text
INTEGRITY VERIFIED
```

5. Modify the contents of `sample_evidence.txt`.
6. Run the verification again.

The calculated hash will now differ from the stored hash and the application will report:

```text
INTEGRITY FAILED
```

This demonstrates how hashing can be used to detect changes to a digital evidence file.

---

## 9. Data Persistence

The application uses local text files for basic persistence.

Examples include:

```text
data/cases.txt
data/evidence.txt
data/custody.txt
```

This allows records to remain available after the application is closed and restarted.

---

## 10. Input Validation and Error Handling

The application performs basic validation for user inputs such as:

* Required fields
* Case identifiers
* Evidence identifiers
* Case status values
* Existing case references
* File paths

Invalid operations are handled using validation checks and exception handling rather than allowing the application to terminate unexpectedly.

---

## 11. Testing

A manual test plan is provided in:

```text
tests/TestPlan.txt
```

The test plan covers:

* Valid case creation
* Duplicate case IDs
* Empty input validation
* Evidence registration
* Invalid case references
* Chain-of-custody records
* Custody history
* Successful integrity verification
* Detection of modified evidence
* Data persistence after restarting the application

---

## 12. Educational Scope

This project is intended for academic and educational use.

It manages fictional investigation records and demonstrates evidence-management and integrity-verification concepts.

It does **not** perform:

* Unauthorized device access
* Password cracking
* Deleted-file recovery
* Private-data extraction from real devices
* Real-world forensic acquisition

---

## 13. Future Enhancements

Possible future improvements include:

* JDBC/MySQL database integration
* JavaFX graphical user interface
* Role-based authentication
* PDF investigation reports
* Advanced metadata and log analysis
* Automated unit testing using JUnit
* Improved evidence search and filtering

---

## 14. Learning Outcomes

This project demonstrates practical use of:

* Classes and objects
* Inheritance
* Encapsulation
* Collections
* Packages
* File handling
* Exception handling
* Input validation
* Date and time APIs
* Cryptographic hashing
* Modular application design
* Version control using Git and GitHub

---

## 15. Author

Nishika Deshmukh
25BAI11562
