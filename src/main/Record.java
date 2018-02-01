package main;

public class Record {
    private int id;
    private String hostname;
    private int downtimeInMinutes;
    private String severity;
    private String attackType;
    private int source;
    private int shift;

    public Record(int id,String hostname,int downtimeInMinutes,String severity,String attackType,int source,int shift) {
        this.id = id;
        this.hostname = hostname;
        this.downtimeInMinutes = downtimeInMinutes;
        this.severity = severity;
        this.attackType = attackType;
        this.source = source;
        this.shift = shift;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getDowntimeInMinutes() {
        return downtimeInMinutes;
    }

    public void setDowntimeInMinutes(int downtimeInMinutes) {
        this.downtimeInMinutes = downtimeInMinutes;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(";").append(hostname).append(";").append(downtimeInMinutes).append(";");
        stringBuilder.append(severity).append(";").append(attackType).append(";").append(source).append(";");
        stringBuilder.append(shift);
        return stringBuilder.toString();
    }
}