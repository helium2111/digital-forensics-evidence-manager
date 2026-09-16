package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evidence {
    private final String evidenceId;
    private final String caseId;
    private String type;
    private String description;
    private String source;
    private String filePath;
    private String collectedBy;
    private final LocalDateTime collectedAt;
    private final String sha256;
    private String status;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Evidence(String evidenceId, String caseId, String type,
                    String description, String source, String filePath,
                    String collectedBy, LocalDateTime collectedAt,
                    String sha256, String status) {
        this.evidenceId = evidenceId;
        this.caseId = caseId;
        this.type = type;
        this.description = description;
        this.source = source;
        this.filePath = filePath;
        this.collectedBy = collectedBy;
        this.collectedAt = collectedAt;
        this.sha256 = sha256;
        this.status = status;
    }

    public String getEvidenceId() { return evidenceId; }
    public String getCaseId() { return caseId; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getSource() { return source; }
    public String getFilePath() { return filePath; }
    public String getCollectedBy() { return collectedBy; }
    public LocalDateTime getCollectedAt() { return collectedAt; }
    public String getSha256() { return sha256; }
    public String getStatus() { return status; }

    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setSource(String source) { this.source = source; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setCollectedBy(String collectedBy) { this.collectedBy = collectedBy; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return String.join("|", evidenceId, caseId, clean(type), clean(description),
                clean(source), clean(filePath), collectedBy, collectedAt.format(FORMATTER),
                sha256, status);
    }

    private String clean(String value) {
        return value.replace("|", "/").replace("\n", " ");
    }

    @Override
    public String toString() {
        return String.format("%s | Case: %s | %-12s | %-24s | %s | %s",
                evidenceId, caseId, type, description, status, sha256.substring(0, Math.min(12, sha256.length())));
    }
}
