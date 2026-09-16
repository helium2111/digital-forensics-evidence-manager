# Digital Forensics Evidence Manager

A Java-based educational project for managing fictional digital investigation cases, evidence records, chain-of-custody events, and evidence integrity using SHA-256 hashing.

## Features

- Case creation, search, listing, and status updates
- Evidence registration and search
- Automatic SHA-256 hashing of evidence files
- Evidence integrity verification
- Chain-of-custody records
- Basic investigation summary report
- File-based persistence
- Input validation and exception handling

## Technologies

- Java 17+
- Java Collections Framework
- Java File I/O / NIO
- Java Time API
- Java Security API (SHA-256)
- Git / GitHub

## Project Structure

```text
src/
├── model/
├── service/
├── util/
└── Main.java

data/
tests/
screenshots/
README.md
statement.md
```

## How to Run

### Option 1: IntelliJ IDEA / Eclipse

Open the project and mark `src` as the Sources Root. Run `Main.java`.

### Option 2: Terminal

From the project root:

```bash
javac -d out src/model/*.java src/service/*.java src/util/*.java src/Main.java
java -cp out Main
```

Java 17 or newer is recommended.

## Integrity Verification Demo

1. Run the program.
2. Create a case, or use an existing case.
3. Add `data/sample_evidence.txt` as evidence.
4. Copy the stored SHA-256 hash.
5. Use **Verify Evidence Integrity**.
6. The result should be `INTEGRITY VERIFIED`.
7. Edit `data/sample_evidence.txt`.
8. Verify again.
9. The result should be `INTEGRITY FAILED`.

## Testing

See `tests/TestPlan.txt` for the Day 1 test plan.

## Important Scope Note

This is an educational evidence-management and integrity-verification project. It does not perform password cracking, deleted-file recovery, unauthorized device access, or extraction of private data from real devices.

## Future Enhancements

- JDBC/MySQL persistence
- JavaFX GUI
- Role-based authentication
- PDF report generation
- More metadata/log analysis
- Automated unit tests with JUnit
