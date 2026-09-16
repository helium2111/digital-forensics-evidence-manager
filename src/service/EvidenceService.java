package service;

import model.Evidence;
import util.FileManager;
import util.HashUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvidenceService {
    private final List<Evidence> evidenceList = new ArrayList<>();
    private final FileManager fileManager;

    public EvidenceService(FileManager fileManager) {
        this.fileManager = fileManager;
        loadEvidence();
    }

    public boolean addEvidence(Evidence evidence) {
        if (findEvidence(evidence.getEvidenceId()) != null) return false;
        evidenceList.add(evidence);
        saveEvidence();
        return true;
    }

    public Evidence createEvidence(String id, String caseId, String type,
                                   String description, String source,
                                   String filePath, String collectedBy) {
        String hash = HashUtil.sha256(filePath);
        String status = hash.startsWith("ERROR") ? "HASH_PENDING" : "VERIFIED";
        return new Evidence(id, caseId, type, description, source, filePath,
                collectedBy, LocalDateTime.now(), hash, status);
    }

    public Evidence findEvidence(String id) {
        for (Evidence e : evidenceList)
            if (e.getEvidenceId().equalsIgnoreCase(id)) return e;
        return null;
    }

    public List<Evidence> getAllEvidence() {
        return new ArrayList<>(evidenceList);
    }

    public List<Evidence> getEvidenceForCase(String caseId) {
        List<Evidence> result = new ArrayList<>();
        for (Evidence e : evidenceList)
            if (e.getCaseId().equalsIgnoreCase(caseId)) result.add(e);
        return result;
    }

    public boolean updateStatus(String evidenceId, String status) {
        Evidence e = findEvidence(evidenceId);
        if (e == null) return false;
        e.setStatus(status);
        saveEvidence();
        return true;
    }

    public String verifyIntegrity(String evidenceId) {
        Evidence e = findEvidence(evidenceId);
        if (e == null) return "Evidence not found.";
        String current = HashUtil.sha256(e.getFilePath());
        if (current.startsWith("ERROR")) return current;
        boolean same = current.equalsIgnoreCase(e.getSha256());
        e.setStatus(same ? "VERIFIED" : "INTEGRITY_FAILED");
        saveEvidence();
        return same
                ? "INTEGRITY VERIFIED: current hash matches the stored hash."
                : "INTEGRITY FAILED: current hash does not match the stored hash.";
    }

    private void loadEvidence() {
        for (String line : fileManager.readLines("evidence.txt")) {
            try {
                String[] p = line.split("\\|", -1);
                if (p.length == 10) {
                    evidenceList.add(new Evidence(p[0], p[1], p[2], p[3], p[4], p[5],
                            p[6], LocalDateTime.parse(p[7]), p[8], p[9]));
                }
            } catch (Exception ignored) { }
        }
    }

    private void saveEvidence() {
        List<String> lines = new ArrayList<>();
        for (Evidence e : evidenceList) lines.add(e.toFileString());
        fileManager.writeLines("evidence.txt", lines);
    }
}
