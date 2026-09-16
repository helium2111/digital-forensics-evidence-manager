import model.CaseRecord;
import model.CustodyRecord;
import model.Evidence;
import service.CaseService;
import service.CustodyService;
import service.EvidenceService;
import util.FileManager;
import util.InputValidator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static CaseService caseService;
    private static EvidenceService evidenceService;
    private static CustodyService custodyService;

    public static void main(String[] args) {
        FileManager fileManager = new FileManager("data");
        caseService = new CaseService(fileManager);
        evidenceService = new EvidenceService(fileManager);
        custodyService = new CustodyService(fileManager);

        createDemoEvidenceFileIfNeeded();
        printBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> caseMenu();
                    case "2" -> evidenceMenu();
                    case "3" -> custodyMenu();
                    case "4" -> integrityMenu();
                    case "5" -> reportMenu();
                    case "6" -> {
                        running = false;
                        System.out.println("\nThank you for using Digital Forensics Evidence Manager.");
                    }
                    default -> System.out.println("Invalid choice. Please select 1-6.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void printBanner() {
        System.out.println("""

                ================================================
                    DIGITAL FORENSICS EVIDENCE MANAGER
                ================================================
                Educational project for evidence and case tracking.
                """);
    }

    private static void printMainMenu() {
        System.out.println("""

                1. Case Management
                2. Evidence Management
                3. Chain of Custody
                4. Verify Evidence Integrity
                5. Generate Investigation Report
                6. Exit
                """);
        System.out.print("Enter choice: ");
    }

    private static void caseMenu() {
        while (true) {
            System.out.println("""

                    -------- CASE MANAGEMENT --------
                    1. Create Case
                    2. View All Cases
                    3. Search Case
                    4. Update Case Status
                    5. Back
                    """);
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createCase();
                case "2" -> listCases();
                case "3" -> searchCase();
                case "4" -> updateCaseStatus();
                case "5" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void createCase() {
        String id = InputValidator.required(prompt("Case ID"), "Case ID");
        if (caseService.findCase(id) != null) {
            System.out.println("A case with this ID already exists.");
            return;
        }
        String title = InputValidator.required(prompt("Case title"), "Case title");
        String description = InputValidator.required(prompt("Description"), "Description");
        String investigator = InputValidator.required(prompt("Investigator ID"), "Investigator ID");

        CaseRecord record = new CaseRecord(id, title, description, investigator,
                LocalDate.now(), "OPEN");

        System.out.println(caseService.addCase(record)
                ? "Case created successfully."
                : "Could not create case.");
    }

    private static void listCases() {
        List<CaseRecord> cases = caseService.getAllCases();
        if (cases.isEmpty()) {
            System.out.println("No cases found.");
            return;
        }
        System.out.println("\nCASE ID | TITLE | STATUS | INVESTIGATOR | DATE");
        cases.forEach(System.out::println);
    }

    private static void searchCase() {
        String id = InputValidator.required(prompt("Enter Case ID"), "Case ID");
        CaseRecord record = caseService.findCase(id);
        System.out.println(record == null ? "Case not found." : record);
    }

    private static void updateCaseStatus() {
        String id = InputValidator.required(prompt("Enter Case ID"), "Case ID");
        String status = InputValidator.required(prompt(
                "New status (OPEN / UNDER_INVESTIGATION / CLOSED)"), "Status").toUpperCase();

        if (!InputValidator.isValidStatus(status)) {
            System.out.println("Invalid status.");
            return;
        }
        System.out.println(caseService.updateStatus(id, status)
                ? "Status updated."
                : "Case not found.");
    }

    private static void evidenceMenu() {
        while (true) {
            System.out.println("""

                    -------- EVIDENCE MANAGEMENT --------
                    1. Add Evidence
                    2. View All Evidence
                    3. Search Evidence
                    4. View Evidence For Case
                    5. Back
                    """);
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addEvidence();
                case "2" -> listEvidence();
                case "3" -> searchEvidence();
                case "4" -> viewEvidenceForCase();
                case "5" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addEvidence() {
        String id = InputValidator.required(prompt("Evidence ID"), "Evidence ID");
        if (evidenceService.findEvidence(id) != null) {
            System.out.println("Evidence ID already exists.");
            return;
        }

        String caseId = InputValidator.required(prompt("Case ID"), "Case ID");
        if (caseService.findCase(caseId) == null) {
            System.out.println("Case does not exist. Create the case first.");
            return;
        }

        String type = InputValidator.required(prompt(
                "Type (Document/Image/Video/Log/Disk Image/Other)"), "Type");
        String description = InputValidator.required(prompt("Description"), "Description");
        String source = InputValidator.required(prompt("Source device/location"), "Source");
        String path = InputValidator.required(prompt("Evidence file path"), "File path");
        String collectedBy = InputValidator.required(prompt("Collected by"), "Collected by");

        Evidence evidence = evidenceService.createEvidence(
                id, caseId, type, description, source, path, collectedBy);

        if (evidence.getSha256().startsWith("ERROR")) {
            System.out.println("Evidence added, but hash could not be calculated: "
                    + evidence.getSha256());
        } else {
            System.out.println("SHA-256 calculated successfully.");
        }

        evidenceService.addEvidence(evidence);
        System.out.println("Evidence added successfully.");
        System.out.println("Stored hash: " + evidence.getSha256());
    }

    private static void listEvidence() {
        List<Evidence> list = evidenceService.getAllEvidence();
        if (list.isEmpty()) {
            System.out.println("No evidence found.");
            return;
        }
        list.forEach(System.out::println);
    }

    private static void searchEvidence() {
        String id = InputValidator.required(prompt("Evidence ID"), "Evidence ID");
        Evidence evidence = evidenceService.findEvidence(id);
        System.out.println(evidence == null ? "Evidence not found." : evidence);
    }

    private static void viewEvidenceForCase() {
        String caseId = InputValidator.required(prompt("Case ID"), "Case ID");
        List<Evidence> list = evidenceService.getEvidenceForCase(caseId);
        if (list.isEmpty()) System.out.println("No evidence found for this case.");
        else list.forEach(System.out::println);
    }

    private static void custodyMenu() {
        String evidenceId = InputValidator.required(prompt("Evidence ID"), "Evidence ID");
        if (evidenceService.findEvidence(evidenceId) == null) {
            System.out.println("Evidence not found.");
            return;
        }

        while (true) {
            System.out.println("""

                    -------- CHAIN OF CUSTODY --------
                    1. Add Custody Record
                    2. View Custody History
                    3. Back
                    """);
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    String from = InputValidator.required(prompt("From"), "From");
                    String to = InputValidator.required(prompt("To"), "To");
                    String action = InputValidator.required(prompt(
                            "Action (COLLECTED/TRANSFERRED/ANALYZED/STORED)"), "Action");
                    String remarks = prompt("Remarks");
                    CustodyRecord record = custodyService.createRecord(
                            evidenceId, from, to, action, remarks);
                    custodyService.addRecord(record);
                    System.out.println("Custody record added: " + record.getCustodyId());
                }
                case "2" -> {
                    List<CustodyRecord> history = custodyService.getForEvidence(evidenceId);
                    if (history.isEmpty()) System.out.println("No custody records found.");
                    else history.forEach(System.out::println);
                }
                case "3" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void integrityMenu() {
        String id = InputValidator.required(prompt("Evidence ID"), "Evidence ID");
        Evidence evidence = evidenceService.findEvidence(id);
        if (evidence == null) {
            System.out.println("Evidence not found.");
            return;
        }

        System.out.println("\nStored SHA-256:");
        System.out.println(evidence.getSha256());
        System.out.println("\n" + evidenceService.verifyIntegrity(id));
        System.out.println("Current status: " + evidenceService.findEvidence(id).getStatus());
    }

    private static void reportMenu() {
        System.out.println("""

                ============ INVESTIGATION REPORT ============
                """);
        List<CaseRecord> cases = caseService.getAllCases();
        List<Evidence> evidence = evidenceService.getAllEvidence();

        System.out.println("Total cases    : " + cases.size());
        System.out.println("Total evidence : " + evidence.size());

        long verified = evidence.stream().filter(e -> e.getStatus().equals("VERIFIED")).count();
        long failed = evidence.stream().filter(e -> e.getStatus().equals("INTEGRITY_FAILED")).count();
        System.out.println("Verified       : " + verified);
        System.out.println("Integrity fail : " + failed);

        if (!cases.isEmpty()) {
            System.out.println("\nCases:");
            cases.forEach(System.out::println);
        }
        if (!evidence.isEmpty()) {
            System.out.println("\nEvidence:");
            evidence.forEach(System.out::println);
        }
        System.out.println("=================================================");
    }

    private static String prompt(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    private static void createDemoEvidenceFileIfNeeded() {
        try {
            Path demo = Paths.get("data", "sample_evidence.txt");
            if (!Files.exists(demo)) {
                Files.createDirectories(demo.getParent());
                Files.writeString(demo,
                        "Synthetic forensic evidence file for educational testing.\n" +
                        "Case: CASE-001\n" +
                        "This file contains no real personal or investigative data.\n");
            }
        } catch (Exception e) {
            System.out.println("Could not create demo evidence file: " + e.getMessage());
        }
    }
}
