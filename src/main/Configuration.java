package main;

public enum Configuration {

    instance;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String records_archive = userDirectory + fileSeparator + "data" + fileSeparator + "records.csv";
}
