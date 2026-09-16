package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustodyRecord {
    private final String custodyId;
    private final String evidenceId;
    private final String fromPerson;
    private final String toPerson;
    private final String action;
    private final LocalDateTime timestamp;
    private final String remarks;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public CustodyRecord(String custodyId, String evidenceId, String fromPerson,
                         String toPerson, String action, LocalDateTime timestamp,
                         String remarks) {
        this.custodyId = custodyId;
        this.evidenceId = evidenceId;
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.action = action;
        this.timestamp = timestamp;
        this.remarks = remarks;
    }

    public String getCustodyId() { return custodyId; }
    public String getEvidenceId() { return evidenceId; }
    public String getFromPerson() { return fromPerson; }
    public String getToPerson() { return toPerson; }
    public String getAction() { return action; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getRemarks() { return remarks; }

    public String toFileString() {
        return String.join("|", custodyId, evidenceId, clean(fromPerson),
                clean(toPerson), clean(action), timestamp.format(FORMATTER), clean(remarks));
    }

    private String clean(String value) {
        return value.replace("|", "/").replace("\n", " ");
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s -> %s | %-12s | %s | %s",
                custodyId, evidenceId, fromPerson, toPerson, action, timestamp, remarks);
    }
}
