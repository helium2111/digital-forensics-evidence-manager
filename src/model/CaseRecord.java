package model;

import java.time.LocalDate;

public class CaseRecord {
    private final String caseId;
    private String title;
    private String description;
    private String investigatorId;
    private final LocalDate createdDate;
    private String status;

    public CaseRecord(String caseId, String title, String description,
                      String investigatorId, LocalDate createdDate, String status) {
        this.caseId = caseId;
        this.title = title;
        this.description = description;
        this.investigatorId = investigatorId;
        this.createdDate = createdDate;
        this.status = status;
    }

    public String getCaseId() { return caseId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInvestigatorId() { return investigatorId; }
    public LocalDate getCreatedDate() { return createdDate; }
    public String getStatus() { return status; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setInvestigatorId(String investigatorId) { this.investigatorId = investigatorId; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return String.join("|", caseId, clean(title), clean(description),
                investigatorId, createdDate.toString(), status);
    }

    private String clean(String value) {
        return value.replace("|", "/").replace("\n", " ");
    }

    @Override
    public String toString() {
        return String.format("%s | %-22s | %-18s | Investigator: %s | %s",
                caseId, title, status, investigatorId, createdDate);
    }
}
