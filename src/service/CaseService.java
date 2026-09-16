package service;

import model.CaseRecord;
import util.FileManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CaseService {
    private final List<CaseRecord> cases = new ArrayList<>();
    private final FileManager fileManager;

    public CaseService(FileManager fileManager) {
        this.fileManager = fileManager;
        loadCases();
    }

    public boolean addCase(CaseRecord record) {
        if (findCase(record.getCaseId()) != null) return false;
        cases.add(record);
        saveCases();
        return true;
    }

    public CaseRecord findCase(String caseId) {
        for (CaseRecord c : cases)
            if (c.getCaseId().equalsIgnoreCase(caseId)) return c;
        return null;
    }

    public List<CaseRecord> getAllCases() {
        return new ArrayList<>(cases);
    }

    public boolean updateStatus(String caseId, String status) {
        CaseRecord c = findCase(caseId);
        if (c == null) return false;
        c.setStatus(status);
        saveCases();
        return true;
    }

    private void loadCases() {
        for (String line : fileManager.readLines("cases.txt")) {
            try {
                String[] p = line.split("\\|", -1);
                if (p.length == 6) {
                    cases.add(new CaseRecord(p[0], p[1], p[2], p[3],
                            LocalDate.parse(p[4]), p[5]));
                }
            } catch (Exception ignored) { }
        }
    }

    private void saveCases() {
        List<String> lines = new ArrayList<>();
        for (CaseRecord c : cases) lines.add(c.toFileString());
        fileManager.writeLines("cases.txt", lines);
    }
}
