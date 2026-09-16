package service;

import model.CustodyRecord;
import util.FileManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustodyService {
    private final List<CustodyRecord> records = new ArrayList<>();
    private final FileManager fileManager;

    public CustodyService(FileManager fileManager) {
        this.fileManager = fileManager;
        loadRecords();
    }

    public boolean addRecord(CustodyRecord record) {
        records.add(record);
        saveRecords();
        return true;
    }

    public CustodyRecord createRecord(String evidenceId, String from, String to,
                                      String action, String remarks) {
        String id = "CUS-" + String.format("%03d", records.size() + 1);
        return new CustodyRecord(id, evidenceId, from, to, action,
                LocalDateTime.now(), remarks);
    }

    public List<CustodyRecord> getForEvidence(String evidenceId) {
        List<CustodyRecord> result = new ArrayList<>();
        for (CustodyRecord r : records)
            if (r.getEvidenceId().equalsIgnoreCase(evidenceId)) result.add(r);
        return result;
    }

    private void loadRecords() {
        for (String line : fileManager.readLines("custody.txt")) {
            try {
                String[] p = line.split("\\|", -1);
                if (p.length == 7) {
                    records.add(new CustodyRecord(p[0], p[1], p[2], p[3], p[4],
                            LocalDateTime.parse(p[5]), p[6]));
                }
            } catch (Exception ignored) { }
        }
    }

    private void saveRecords() {
        List<String> lines = new ArrayList<>();
        for (CustodyRecord r : records) lines.add(r.toFileString());
        fileManager.writeLines("custody.txt", lines);
    }
}
